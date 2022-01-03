package eobrazovanje.api;

import eobrazovanje.dto.GraphDTO;
import eobrazovanje.dto.KstLibParamsDTO;
import eobrazovanje.dto.KstLibResponseDTO;
import eobrazovanje.model.*;
import eobrazovanje.service.IDomainProblemService;
import eobrazovanje.service.IKnowledgeSpaceService;
import eobrazovanje.service.impl.CourseService;
import eobrazovanje.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<GraphDTO> FindKnowledgeSpaceById(@PathVariable("id") Long id){
        KnowledgeSpace ks = knowledgeSpaceService.findById(id);
        GraphDTO ksDTO = Converter.knowledgeSpaceToDTO(ks);
        return new ResponseEntity<>(ksDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(value = "course/{course_id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KnowledgeSpace> createKnowledgeSpace(@PathVariable("course_id") Long courseId, @RequestBody GraphDTO graphDTO) throws MethodArgumentNotValidException {
        KnowledgeSpace ks = Converter.dtoToKnowledgeSpace(graphDTO,null);
        Domain domain = new Domain();
        domain.setId(courseService.findDomainIdByCourseId(courseId));
        ks.setDomain(domain);
        return new ResponseEntity<>(knowledgeSpaceService.save(ks), HttpStatus.OK);
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
        ks.setDomain(domainProblems.get(0).getDomain());


        ArrayList<KnowledgeSpaceNode> ksNodes = new ArrayList<>();
        int cnt = 0;
        for(DomainProblem dp: domainProblems){
            KnowledgeSpaceNode ksNode = new KnowledgeSpaceNode();
            ksNode.setNode(dp);
            ksNode.setSize(new Size(150.0, 150.0));
            ksNode.setCoordinates(new Coordinates(cnt*30.0, cnt*30.0));
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
        return new ResponseEntity<>(knowledgeSpaceService.save(ks), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(value = "/implications", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> getImplications(@RequestBody KstLibParamsDTO paramsDTO){
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


}
