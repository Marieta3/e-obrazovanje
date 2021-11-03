package eobrazovanje.service;

import eobrazovanje.model.Course;

public interface ICourseService {

    Course findById(Long id);

    Course save(Course course);
}
