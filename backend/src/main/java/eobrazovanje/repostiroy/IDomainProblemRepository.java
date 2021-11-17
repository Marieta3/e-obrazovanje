package eobrazovanje.repostiroy;

import eobrazovanje.model.DomainProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDomainProblemRepository extends JpaRepository<DomainProblem, Long> {
}
