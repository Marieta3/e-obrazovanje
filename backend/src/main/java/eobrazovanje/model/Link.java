package eobrazovanje.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "links")
public class Link {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "start_domain_problem_id", referencedColumnName = "id", unique = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private DomainProblem startNode;

    @JoinColumn(name = "end_domain_problem_id", referencedColumnName = "id", unique = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private DomainProblem endNode;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonBackReference("links")
    private KnowledgeSpace knowledgeSpace;

    public Link() {
    }

    public Link(Long id, DomainProblem startNode, DomainProblem endNode, KnowledgeSpace knowledgeSpace) {
        this.id = id;
        this.startNode = startNode;
        this.endNode = endNode;
        this.knowledgeSpace = knowledgeSpace;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DomainProblem getStartNode() {
        return startNode;
    }

    public void setStartNode(DomainProblem startNode) {
        this.startNode = startNode;
    }

    public DomainProblem getEndNode() {
        return endNode;
    }

    public void setEndNode(DomainProblem endNode) {
        this.endNode = endNode;
    }

    public KnowledgeSpace getKnowledgeSpace() {
        return knowledgeSpace;
    }

    public void setKnowledgeSpace(KnowledgeSpace knowledgeSpace) {
        this.knowledgeSpace = knowledgeSpace;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", startNode=" + startNode +
                ", endNode=" + endNode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link)) return false;
        Link link = (Link) o;
        return Objects.equals(getId(), link.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
