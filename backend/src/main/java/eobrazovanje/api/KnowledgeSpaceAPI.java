package eobrazovanje.api;

import eobrazovanje.dto.GraphDTO;
import eobrazovanje.model.Domain;
import eobrazovanje.model.DomainProblem;
import eobrazovanje.model.KnowledgeSpace;
import eobrazovanje.service.IDomainProblemService;
import eobrazovanje.service.IKnowledgeSpaceService;
import eobrazovanje.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/knowledge-spaces")
public class KnowledgeSpaceAPI {

    @Autowired
    private IKnowledgeSpaceService knowledgeSpaceService;

    @Autowired
    private IDomainProblemService domainProblemService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<GraphDTO> FindKnowledgeSpaceById(@PathVariable("id") Long id){
        KnowledgeSpace ks = knowledgeSpaceService.findById(id);
        GraphDTO ksDTO = Converter.knowledgeSpaceToDTO(ks);
        return new ResponseEntity<>(ksDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(value = "domain/{domain_id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KnowledgeSpace> createKnowledgeSpace(@PathVariable("domain_id") Long domainId, @RequestBody GraphDTO graphDTO) throws MethodArgumentNotValidException {
        KnowledgeSpace ks = Converter.dtoToKnowledgeSpace(graphDTO);
        Domain domain = new Domain();
        domain.setId(domainId);
        ks.setDomain(domain);
        return new ResponseEntity<>(knowledgeSpaceService.save(ks), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PutMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KnowledgeSpace> updateKnowledgeSpace(
            @PathVariable(value = "id") Long ksID,
            @RequestBody GraphDTO graphDTO) throws MethodArgumentNotValidException {
        KnowledgeSpace ks = Converter.dtoToKnowledgeSpace(graphDTO);
        return new ResponseEntity<>(knowledgeSpaceService.update(ksID, ks), HttpStatus.OK);

    }

}
