package eobrazovanje.repostiroy;

import eobrazovanje.model.DomainProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDomainProblemRepository extends JpaRepository<DomainProblem, Long> {
    @Query("select dp from DomainProblem dp where domain.id = ?1")
    List<DomainProblem> getAllByDomainId(Long domainId);
}
