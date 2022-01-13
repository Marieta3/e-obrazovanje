package eobrazovanje.repostiroy;

import eobrazovanje.model.Answer;
import eobrazovanje.model.DomainProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface IAnswerRepository extends JpaRepository<Answer,Long> {

    @Query("select a from Answer a WHERE a.id IN ?1")
    Set<Answer> findByIds(ArrayList<Long> ids);

    @Query("select a.question.domainProblem from Answer a WHERE a.id IN ?1")
    DomainProblem getDomainProblemByAnswerId(Long answerId);

}
