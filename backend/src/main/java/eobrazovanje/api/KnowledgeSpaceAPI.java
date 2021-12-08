package eobrazovanje.api;

import eobrazovanje.dto.GraphDTO;
import eobrazovanje.dto.KstLibParamsDTO;
import eobrazovanje.model.Domain;
import eobrazovanje.model.DomainProblem;
import eobrazovanje.model.KnowledgeSpace;
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
    @PostMapping(value = "/impl/{domain_id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> getEmployees(@PathVariable(value = "domain_id") Long domainId,@RequestBody KstLibParamsDTO paramsDTO)
    {
        List<DomainProblem> domainProblems = domainProblemService.findByDomainId(domainId);
        paramsDTO.setItems(domainProblems.size());
        final String uri = "http://127.0.0.1:5000/impl";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<KstLibParamsDTO> httpEntity = new HttpEntity<KstLibParamsDTO>(paramsDTO, headers);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        System.out.println(result.getBody());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(value = "/impl", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> getEmployees(@RequestBody KstLibParamsDTO paramsDTO){
        final String uri = "http://127.0.0.1:5000/impl";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<KstLibParamsDTO> httpEntity = new HttpEntity<KstLibParamsDTO>(paramsDTO, headers);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        System.out.println(result.getBody());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
