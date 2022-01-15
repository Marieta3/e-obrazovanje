package eobrazovanje.repostiroy;

import eobrazovanje.model.Coordinates;
import eobrazovanje.model.KnowledgeSpace;
import eobrazovanje.model.KnowledgeSpaceNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IKnowledgeSpaceNodeRepository extends JpaRepository<KnowledgeSpaceNode, Long> {
    @Query(value = "select ksn from KnowledgeSpaceNode ksn where ksn.node.id = ?1")
    List<KnowledgeSpaceNode> getCoordinatesForDomainProblem (Long domainProblemId);
}
