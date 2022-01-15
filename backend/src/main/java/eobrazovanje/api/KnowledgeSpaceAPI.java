package eobrazovanje.api;

import eobrazovanje.dto.CompareGraphsDTO;
import eobrazovanje.dto.GraphDTO;
import eobrazovanje.dto.KstLibParamsDTO;
import eobrazovanje.dto.KstLibResponseDTO;
import eobrazovanje.model.*;
import eobrazovanje.service.IDomainProblemService;
import eobrazovanje.service.IDomainService;
import eobrazovanje.service.IKnowledgeSpaceService;
import eobrazovanje.service.impl.CourseService;
import eobrazovanje.util.Converter;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/knowledge-spaces")
public class KnowledgeSpaceAPI {

    @Autowired
    private IKnowledgeSpaceService knowledgeSpaceService;

    @Autowired
    private IDomainProblemService domainProblemService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private IDomainService domainService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<GraphDTO> FindKnowledgeSpaceById(@PathVariable("id") Long id){
        KnowledgeSpace ks = knowledgeSpaceService.findById(id);
        GraphDTO ksDTO = Converter.knowledgeSpaceToDTO(ks);
        return new ResponseEntity<>(ksDTO, HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(value = "course/{course_id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KnowledgeSpace> createKnowledgeSpace(@PathVariable("course_id") Long courseId, @RequestBody GraphDTO graphDTO) throws MethodArgumentNotValidException {
        KnowledgeSpace ks = Converter.dtoToKnowledgeSpace(graphDTO,null);
        Domain domain = new Domain();
        ks.setType(KnowledgeSpaceType.MANUAL);
        domain.setId(courseService.findDomainIdByCourseId(courseId));
        ks.setDomain(domain);
        KnowledgeSpace createdKnowledgeSpace = knowledgeSpaceService.save(ks);
        knowledgeSpaceService.setKnowledgeSpaceToBeActive(createdKnowledgeSpace);
        return new ResponseEntity<>(createdKnowledgeSpace, HttpStatus.OK);
    }

    @CrossOrigin()
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PutMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KnowledgeSpace> updateKnowledgeSpace(
            @PathVariable(value = "id") Long ksID,
            @RequestBody GraphDTO graphDTO) throws MethodArgumentNotValidException {
        KnowledgeSpace ks = Converter.dtoToKnowledgeSpace(graphDTO, ksID);
        return new ResponseEntity<>(knowledgeSpaceService.update(ksID, ks), HttpStatus.OK);

    }

    //@GetMapping("/impl")
    @Transactional
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(value = "/implications/{domain_id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> getImplications(@PathVariable(value = "domain_id") Long domainId,@RequestBody KstLibParamsDTO paramsDTO)
    {
        List<DomainProblem> domainProblems = domainProblemService.findByDomainId(domainId);
        paramsDTO.setItems(domainProblems.size());
        //TODO: promeniti na dinamicku putanju
        final String uri = "http://127.0.0.1:5000/implications";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<KstLibParamsDTO> httpEntity = new HttpEntity<KstLibParamsDTO>(paramsDTO, headers);

        ResponseEntity<KstLibResponseDTO> result = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, KstLibResponseDTO.class);
        System.out.println(result.getBody());
        KstLibResponseDTO response = result.getBody();

        KnowledgeSpace ks = new KnowledgeSpace();
        ks.setTitle("iita knowledge space ");
        ks.setCreatedAt(new Date());
        ks.setType(KnowledgeSpaceType.IITA);
        ks.setDomain(domainProblems.get(0).getDomain());

        ArrayList<KnowledgeSpaceNode> ksNodes = new ArrayList<>();
        int cnt = 0;
        for(DomainProblem dp: domainProblems){
            Coordinates coords = knowledgeSpaceService.getCoordinatesForDomainProblem(dp.getId());
            KnowledgeSpaceNode ksNode = new KnowledgeSpaceNode();
            ksNode.setNode(dp);
            ksNode.setSize(new Size(150.0, 150.0));
            ksNode.setCoordinates(coords);
            ksNodes.add(ksNode);
            cnt++;
        }
        for(int i=0; i<response.getImplications().size(); i++){
            ArrayList<Integer> impl = response.getImplications().get(i);
            int start = impl.get(0);
            int end = impl.get(1);

            Link newLink = new Link();
            newLink.setStartNode(ksNodes.get(start));
            newLink.setEndNode(ksNodes.get(end));
            newLink.setKnowledgeSpace(ks);
            ks.getLinks().add(newLink);
            //System.out.println(implications.get(i));
        }

        for(int i=0; i<response.getPaths().size(); i++){
            ArrayList<Integer> path = response.getPaths().get(i);
            State state = new State();
            state.setKnowledgeSpace(ks);
            state.setProbability(1.0/response.getPaths().size());
            for(int j: path){
                DomainProblem dp1 = domainProblems.get(j);
                state.getDomainProblems().add(dp1);
            }
            ks.getStates().add(state);
        }
        KnowledgeSpace createdKnowledgeSpace = knowledgeSpaceService.save(ks);
        knowledgeSpaceService.setKnowledgeSpaceToBeActive(createdKnowledgeSpace);
        return new ResponseEntity<>(createdKnowledgeSpace, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_STUDENT')")
    @GetMapping(value = "/course/{courseId}/active")
    public  ResponseEntity<KnowledgeSpace> getImplications(@PathVariable("courseId") Long courseId){
        Domain d = domainService.findByCourseId(courseId);
        return new ResponseEntity<>(d.getActiveKnowledgeSpace(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(value = "/implications", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> getImplications(@RequestBody KstLibParamsDTO paramsDTO){
        //TODO: promeniti na dinamicku putanju
        final String uri = "http://127.0.0.1:5000/impl";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<KstLibParamsDTO> httpEntity = new HttpEntity<KstLibParamsDTO>(paramsDTO, headers);

        ResponseEntity<ArrayList> result = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, ArrayList.class);
        System.out.println(result.getBody());
        ArrayList lista = result.getBody();
        for(int i=0; i<result.getBody().size(); i++){
            ArrayList item = (ArrayList) lista.get(i);
            System.out.println(item.get(0));
            System.out.println(item.get(1));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/compare-ks/{course_id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> findKnowledgeSpacesByICoursed(@PathVariable("course_id") Long course_id){
        Domain domain = domainService.findByCourseId(course_id);
        ArrayList<GraphDTO> graphs = new ArrayList<>();
        for(KnowledgeSpace ks: domain.getKnowledgeSpaces()){
            graphs.add(Converter.knowledgeSpaceToDTO(ks));
        }
        System.out.println(graphs.size());
        return new ResponseEntity<>(graphs, HttpStatus.OK);
    }

    @GetMapping("/compare-ks1/{course_id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> findKnowledgeSpacesByICoursed1(@PathVariable("course_id") Long course_id){
        Domain domain = domainService.findByCourseId(course_id);
        ArrayList<KnowledgeSpace> ks_list = new ArrayList<>(domain.getKnowledgeSpaces());
        KnowledgeSpace ks1 = ks_list.get(0);
        KnowledgeSpace ks2 = ks_list.get(1);
        ArrayList<Link> common_links = new ArrayList<>();
        ArrayList<Link> ks1_links = new ArrayList<>(ks1.getLinks());
        ArrayList<Link> ks2_links = new ArrayList<>(ks2.getLinks());

        for(Link l1: ks1.getLinks()){
            KnowledgeSpaceNode startKsn = l1.getStartNode();
            startKsn.setId(startKsn.getNode().getId());
            KnowledgeSpaceNode endKsn = l1.getEndNode();
            endKsn.setId(endKsn.getNode().getId());
            for(Link l2: ks2.getLinks()){
                KnowledgeSpaceNode startKsn2 = l2.getStartNode();
                startKsn2.setId(startKsn2.getNode().getId());
                KnowledgeSpaceNode endKsn2 = l2.getEndNode();
                endKsn2.setId(endKsn2.getNode().getId());
                if(l1.equalsByNodes(l2)){
                    common_links.add(l1);
                    ks1_links.remove(l1);
                    ks2_links.remove(l2);
                }
            }
        }

        ks1.setLinks(new HashSet<>(ks1_links));
        ks2.setLinks(new HashSet<>(ks2_links));

        System.out.println("Common links: "+common_links.size());
        System.out.println("KS1 links: "+ks1_links.size());
        System.out.println("KS2 links: "+ks2_links.size());

        for(Link l: common_links){
            System.out.println("Start node: "+l.getStartNode().getNode().getId());
            System.out.println("End node: "+l.getEndNode().getNode().getId());
            System.out.println("-----------------------------------------");
        }
        System.out.println("********************KS1***************************");
        for(Link l: ks1_links){
            System.out.println("Start node: "+l.getStartNode().getNode().getId());
            System.out.println("End node: "+l.getEndNode().getNode().getId());
            System.out.println("-----------------------------------------");
        }
        System.out.println("*******************KS2****************************");
        for(Link l: ks2_links){
            System.out.println("Start node: "+l.getStartNode().getNode().getId());
            System.out.println("End node: "+l.getEndNode().getNode().getId());
            System.out.println("-----------------------------------------");
        }


        return new ResponseEntity<>(Converter.createCompareGraphsDTO(ks1, ks2, common_links), HttpStatus.OK);
    }

}
