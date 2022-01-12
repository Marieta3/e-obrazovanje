package eobrazovanje.repostiroy;

import eobrazovanje.model.Answer;
import eobrazovanje.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Set;

public interface IDomainRepository extends JpaRepository<Domain,Long> {

    @Query("select d from Domain d WHERE d.course.id = ?1")
    public Domain findByCourseId(Long course_id);

}
