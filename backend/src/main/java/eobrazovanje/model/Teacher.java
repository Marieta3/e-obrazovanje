package eobrazovanje.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@DiscriminatorValue("teacher")
public class Teacher extends User{

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("teacherCourses")
    private Set<Course> courses = new HashSet<>();

    public Teacher() {
        super();
    }

    public Teacher(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "courses=" + courses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        if (getId() == null) {
            if (teacher.getId() != null)
                return false;
        } else if (!getId().equals(teacher.getId()))
            return false;
        return true;
    }


}
