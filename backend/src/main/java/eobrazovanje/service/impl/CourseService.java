package eobrazovanje.service.impl;

import eobrazovanje.model.Course;
import eobrazovanje.repostiroy.ICourseRepository;
import eobrazovanje.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService implements ICourseService {

    @Autowired
    private ICourseRepository courseRepository;


    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findByTeacherId(Long id) {
        return courseRepository.findByTeacherId(id);
    }

    @Override
    public List<Course> findByTeacherUsername(String username) {
        return courseRepository.findByTeacherUsername(username);
    }

    @Override
    public Long findDomainIdByCourseId(Long courseId) {
        return courseRepository.findDomainIdByCourseId(courseId);
    }
}
