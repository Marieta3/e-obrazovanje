package eobrazovanje.repostiroy;

import eobrazovanje.model.Coordinates;
import eobrazovanje.model.KnowledgeSpace;
import eobrazovanje.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IKnowledgeSpaceRepository extends JpaRepository<KnowledgeSpace, Long> {

    @Query("select ks from KnowledgeSpace ks WHERE ks.domain.id = ?1")
    List<KnowledgeSpace> findAllByDomainId(Long id);

    @Modifying
    @Query(value = "delete from Link l where l.knowledgeSpace.id=?1")
    int deleteLinksForKnowledgeSpace(Long knowledgeSpaceId);

    @Modifying
    @Query(value = "update Domain d set d.activeKnowledgeSpace.id = ?1 where d.id = ?2")
    void setKnowledgeSpaceToBeActive(Long knowledgeSpaceId, Long domainId);

}
