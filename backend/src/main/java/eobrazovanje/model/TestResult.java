package eobrazovanje.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "test_results")
public class TestResult {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "points")
    private int points;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "test_result_answer", joinColumns = @JoinColumn(name = "test_result_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "answer_id", referencedColumnName = "id"))
    @JsonBackReference
    private Set<Answer> answers = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonBackReference("studentTestResults")
    private Student student;

    @JoinColumn(name = "test_id", unique = false)
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonBackReference
    private Test test;

    public TestResult(Long id, Date startTime, Date endTime, int points, Set<Answer> answers, Student student, Test test) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.points = points;
        this.answers = answers;
        this.student = student;
        this.test = test;
    }

    public TestResult() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", points=" + points +
                ", answers=" + answers.size() +
                ", student=" + student.getFirstName() +
                ", test=" + test.getTitle() +
                '}';
    }

    public void addAnswer(Answer answer){
        answers.add(answer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestResult)) return false;
        TestResult that = (TestResult) o;
        return Objects.equals(getId(), that.getId());
    }

}
