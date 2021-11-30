package eobrazovanje.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "domain_problems")
public class DomainProblem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;


    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonBackReference("domainProblems")
    private Domain domain;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "node")
    @Getter @Setter
    private List<KnowledgeSpaceNode> knowledgeSpaceNodeList;

    public DomainProblem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DomainProblem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", domain=" + domain +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DomainProblem)) return false;
        DomainProblem that = (DomainProblem) o;
        return Objects.equals(getId(), that.getId());
    }

}
