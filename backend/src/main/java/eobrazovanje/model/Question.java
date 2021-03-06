package eobrazovanje.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "is_randomize")
    private boolean isRandomize;

//    @Column(name = "points")
//    private int points;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonBackReference("testQuestions")
    private Test test;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("questionAnswers")
    private Set<Answer> answers = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "domain_problem_id", unique = false)
    /*@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @JoinColumn(name = "domain_problem_id", referencedColumnName = "id", unique = false)
    @ManyToOne(cascade = CascadeType.ALL)*/
    private DomainProblem domainProblem;

    public Question(Long id, String text, boolean isRandomize, Test test, Set<Answer> answers, DomainProblem domainProblem) {
        this.id = id;
        this.text = text;
        this.isRandomize = isRandomize;
        this.test = test;
        this.answers = answers;
        this.domainProblem = domainProblem;
    }

    public Question() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRandomize() {
        return isRandomize;
    }

    public void setRandomize(boolean randomize) {
        isRandomize = randomize;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public DomainProblem getDomainProblem() {
        return domainProblem;
    }

    public void setDomainProblem(DomainProblem domainProblem) {
        this.domainProblem = domainProblem;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isRandomize=" + isRandomize +
                ", test=" + test +
                ", answers=" + answers +
                ", domainProblem=" + domainProblem +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return Objects.equals(getId(), question.getId());
    }

}
