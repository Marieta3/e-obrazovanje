package eobrazovanje.repostiroy;

import eobrazovanje.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface IAnswerRepository extends JpaRepository<Answer,Long> {

    @Query("select a from Answer a WHERE a.id IN ?1")
    public Set<Answer> findByIds(ArrayList<Long> ids);

}
