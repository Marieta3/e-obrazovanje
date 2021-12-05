package eobrazovanje.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

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
    @Getter @Setter
    private KnowledgeSpaceNode startNode;

    @JoinColumn(name = "end_domain_problem_id", referencedColumnName = "id", unique = false)
    @ManyToOne(cascade = CascadeType.ALL)
    @Getter @Setter
    private KnowledgeSpaceNode endNode;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonBackReference("links")
    private KnowledgeSpace knowledgeSpace;

    public Link() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
