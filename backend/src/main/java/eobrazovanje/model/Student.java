package eobrazovanje.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("student")
public class Student extends User{

    private static final long serialVersionUID = 1L;

    @Column(name="index_number")
    private String indexNumber;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("studentTestResults")
    private Set<TestResult> testResults = new HashSet<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("studentCourses")
    private Set<StudentCourse> studentCourses = new HashSet<>();


    public Student() {
        super();
    }

    public Student(String indexNumber, Set<TestResult> testResults, Set<StudentCourse> studentCourses) {
        this.indexNumber = indexNumber;
        this.testResults = testResults;
        this.studentCourses = studentCourses;
    }

    public Set<TestResult> getTestResults() {
        return testResults;
    }

    public void setTestResults(Set<TestResult> testResults) {
        this.testResults = testResults;
    }

    public Set<StudentCourse> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(Set<StudentCourse> studentCourses) {
        this.studentCourses = studentCourses;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    @Override
    public String toString() {
        return "Student{" +
                "indexNumber='" + indexNumber + '\'' +
                ", testResults=" + testResults +
                ", studentCourses=" + studentCourses +
                '}';
    }
}
