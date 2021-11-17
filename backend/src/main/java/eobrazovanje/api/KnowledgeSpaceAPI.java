package eobrazovanje.api;

import eobrazovanje.dto.DomainProblemDTO;
import eobrazovanje.dto.KnowledgeSpaceDTO;
import eobrazovanje.dto.LinkDTO;
import eobrazovanje.model.DomainProblem;
import eobrazovanje.model.KnowledgeSpace;
import eobrazovanje.model.Link;
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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/knowledge-spaces")
public class KnowledgeSpaceAPI {

    @Autowired
    private IKnowledgeSpaceService knowledgeSpaceService;

    @Autowired
    private IDomainProblemService domainProblemService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<KnowledgeSpaceDTO> FindKnowledgeSpaceById(@PathVariable("id") Long id){
        KnowledgeSpace ks = knowledgeSpaceService.findById(id);
        KnowledgeSpaceDTO ksDTO = Converter.knowledgeSpaceToDTO(ks);
        return new ResponseEntity<>(ksDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KnowledgeSpace> createKnowledgeSpace(@RequestBody KnowledgeSpaceDTO ksDTO) throws MethodArgumentNotValidException {
        KnowledgeSpace ks = Converter.dtoToKnowledgeSpace(ksDTO);
        return new ResponseEntity<>(knowledgeSpaceService.save(ks), HttpStatus.OK);
    }

}
