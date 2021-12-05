package eobrazovanje.repostiroy;

import eobrazovanje.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICourseRepository extends JpaRepository<Course,Long> {

    @Query("select c from Course c WHERE c.teacher.id = ?1")
    public List<Course> findByTeacherId(Long id);

    @Query("select c from Course c WHERE c.teacher.username = ?1")
    public List<Course> findByTeacherUsername(String username);

    @Query("select c.domain.id from Course c Where c.id = ?1")
    public Long findDomainIdByCourseId(Long courseId);
}
