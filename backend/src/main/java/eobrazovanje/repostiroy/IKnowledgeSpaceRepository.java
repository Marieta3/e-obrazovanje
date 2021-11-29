package eobrazovanje.repostiroy;

import eobrazovanje.model.KnowledgeSpace;
import eobrazovanje.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IKnowledgeSpaceRepository extends JpaRepository<KnowledgeSpace, Long> {

    @Query("select ks from KnowledgeSpace ks WHERE ks.domain.id = ?1")
    public List<KnowledgeSpace> findAllByDomainId(Long id);
}
