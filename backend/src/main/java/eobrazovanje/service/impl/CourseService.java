package eobrazovanje.service.impl;

import eobrazovanje.model.Course;
import eobrazovanje.repostiroy.ICourseRepository;
import eobrazovanje.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
