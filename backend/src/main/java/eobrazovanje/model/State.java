package eobrazovanje.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "states")
public class State {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "domain_problem_in_state", joinColumns = @JoinColumn(name = "state_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "domain_problem_id", referencedColumnName = "id"))
    @JsonBackReference
    private Set<DomainProblem> domainProblems = new HashSet<>();

    @Column(name = "probability")
    private Double probability;

    /*@JoinColumn(name = "knowledge_space_id", unique = false)
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonBackReference*/
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonBackReference("states")
    private KnowledgeSpace knowledgeSpace;

    public State() {
    }

    public State(Long id, Set<DomainProblem> domainProblems, Double probability, KnowledgeSpace knowledgeSpace) {
        this.id = id;
        this.domainProblems = domainProblems;
        this.probability = probability;
        this.knowledgeSpace = knowledgeSpace;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<DomainProblem> getDomainProblems() {
        return domainProblems;
    }

    public void setDomainProblems(Set<DomainProblem> domainProblems) {
        this.domainProblems = domainProblems;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public KnowledgeSpace getKnowledgeSpace() {
        return knowledgeSpace;
    }

    public void setKnowledgeSpace(KnowledgeSpace knowledgeSpace) {
        this.knowledgeSpace = knowledgeSpace;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", domainProblems=" + domainProblems +
                ", probability=" + probability +
                ", knowledgeSpace=" + knowledgeSpace +
                '}';
    }
}
