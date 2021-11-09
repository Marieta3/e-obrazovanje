package eobrazovanje.repostiroy;

import eobrazovanje.model.Course;
import eobrazovanje.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITestRepository extends JpaRepository<Test,Long> {
    @Query("select t from Test t WHERE t.course.id = ?1")
    public List<Test> findByCourseId(Long id);
}
