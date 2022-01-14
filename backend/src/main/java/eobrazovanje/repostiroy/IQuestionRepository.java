package eobrazovanje.repostiroy;

import eobrazovanje.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IQuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.domainProblem.id=?1 ORDER BY RANDOM()")
    List<Question> getRandomQuestionForDomainProblemId(Long domainProblemId);

    @Query("SELECT q.domainProblem.id FROM Question q WHERE q.id = ?1")
    Long getDomainProblemIdByQuestionId(Long questionId);
}
