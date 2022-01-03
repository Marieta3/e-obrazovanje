package eobrazovanje.repostiroy;

import eobrazovanje.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IQuestionRepository extends JpaRepository<Long, Question> {
    @Query("SELECT q FROM Question q WHERE q.domainProblemId=?1 ORDER BY RANDOM() LIMIT 1 ")
    Question getRandomQuestionForDomainProblemId(Long domainProblemId);
}
