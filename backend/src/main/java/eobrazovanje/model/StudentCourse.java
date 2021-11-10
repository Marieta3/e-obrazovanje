package eobrazovanje.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "students_courses")
public class StudentCourse {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "student_id", referencedColumnName = "id", unique = false, nullable = false)
    @JoinColumn(name = "student_index", referencedColumnName = "index_number", unique = false, nullable = false)
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JsonBackReference("studentCourses")
    private Student student;

    @JoinColumn(name = "course_id", unique = false)
    @OneToOne(cascade = CascadeType.ALL)
    private Course course;


    private boolean completed;


    public StudentCourse(Long id, Student student, Course course, boolean completed) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.completed = completed;
    }

    public StudentCourse() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentCourse)) return false;
        StudentCourse that = (StudentCourse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "StudentCourse{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                ", completed=" + completed +
                '}';
    }
}
