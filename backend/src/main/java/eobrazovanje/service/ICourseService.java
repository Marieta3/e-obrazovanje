package eobrazovanje.service;

import eobrazovanje.model.Course;

import java.util.List;

public interface ICourseService {

    Course findById(Long id);

    Course save(Course course);

    List<Course> findAll();

    List<Course> findByTeacherId(Long id);

    List<Course> findByTeacherUsername(String username);
}
