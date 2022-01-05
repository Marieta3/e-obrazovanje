package eobrazovanje.repostiroy;

import eobrazovanje.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IQuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.domainProblem.id=?1 ORDER BY RANDOM()")
    Question getRandomQuestionForDomainProblemId(Long domainProblemId);
}
