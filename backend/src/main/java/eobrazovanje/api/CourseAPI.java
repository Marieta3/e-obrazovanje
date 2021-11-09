package eobrazovanje.api;

import eobrazovanje.dto.CourseDTO;
import eobrazovanje.dto.TestDTO;
import eobrazovanje.model.Course;
import eobrazovanje.model.Teacher;
import eobrazovanje.model.Test;
import eobrazovanje.service.ICourseService;
import eobrazovanje.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseAPI {

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_ADMIN', 'ROLE_TEACHER')")
    public Course FindCourseById(@PathVariable("id") Long id){
        return courseService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> createCourse(@RequestBody CourseDTO courseDTO) throws MethodArgumentNotValidException {
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setTeacher((Teacher) userService.findById(courseDTO.getTeacherId()));
        return new ResponseEntity<>(courseService.save(course), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> getAllCourses() {
        // admin vidi sve kurseve
        return new ResponseEntity<>(courseService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value="/all/{teacher_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> getAllCoursesByTeacherId(@PathVariable("teacher_id") Long teacher_id) {
        //admin moze da pronadje kurseve nekog konkretnog nastavnika
        return new ResponseEntity<>(courseService.findByTeacherId(teacher_id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @GetMapping(value="/my", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> getAllCoursesByTeacher(Principal user) {
        //pronalazenje kurseva na osnovu ulogovanog korisnika koji mora biti teacher
        //nastavnik vidi samo svoje kurseve
        // username je unique??
        return new ResponseEntity<>(courseService.findByTeacherUsername(user.getName()), HttpStatus.OK);
    }
}
