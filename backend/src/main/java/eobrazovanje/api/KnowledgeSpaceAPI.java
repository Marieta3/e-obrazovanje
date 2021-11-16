package eobrazovanje.api;

import eobrazovanje.dto.DomainProblemDTO;
import eobrazovanje.dto.KnowledgeSpaceDTO;
import eobrazovanje.dto.LinkDTO;
import eobrazovanje.model.DomainProblem;
import eobrazovanje.model.KnowledgeSpace;
import eobrazovanje.model.Link;
import eobrazovanje.service.IDomainProblemService;
import eobrazovanje.service.IKnowledgeSpaceService;
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
    public KnowledgeSpace FindKnowledgeSpaceById(@PathVariable("id") Long id){
        return knowledgeSpaceService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KnowledgeSpace> createKnowledgeSpace(@RequestBody KnowledgeSpaceDTO ksDTO) throws MethodArgumentNotValidException {
        KnowledgeSpace ks = new KnowledgeSpace();
        ks.setTitle(ksDTO.getTitle());
        Map<String, DomainProblem> mapa = new HashMap<>();
        for(DomainProblemDTO dpDTO: ksDTO.getNodes()){
            DomainProblem dp = new DomainProblem();
            dp.setTitle(dpDTO.getTitle());
            dp.setWidth(dpDTO.getSize().getWidth());
            dp.setHeight(dpDTO.getSize().getHeight());
            dp.setxCoordinate(dpDTO.getCoordinates().getX());
            dp.setyCoordinate(dpDTO.getCoordinates().getY());
            dp.setKnowledgeSpace(ks);
            //DomainProblem dpSaved = domainProblemService.save(dp);
            mapa.put(dpDTO.getId(), dp);
            ks.getDomainProblems().add(dp);
        }

        for(LinkDTO linkDTO: ksDTO.getLinks()){
            Link link = new Link();
            link.setKnowledgeSpace(ks);
            link.setStartNode(mapa.get(linkDTO.getStart_id()));
            link.setEndNode(mapa.get(linkDTO.getEnd_id()));
            ks.getLinks().add(link);
        }
        return new ResponseEntity<>(knowledgeSpaceService.save(ks), HttpStatus.OK);
    }
}
