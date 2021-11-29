package eobrazovanje.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "domains")
public class Domain {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "domain", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("domainProblems")
    private Set<DomainProblem> domainProblems = new HashSet<>();

    @OneToMany(mappedBy = "domain", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("knowledgeSpaces")
    private Set<KnowledgeSpace> knowledgeSpaces = new HashSet<>();

    @OneToOne(mappedBy = "domain")
    @Getter @Setter
    private Course course;

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

    public Set<DomainProblem> getDomainProblems() {
        return domainProblems;
    }

    public void setDomainProblems(Set<DomainProblem> domainProblems) {
        this.domainProblems = domainProblems;
    }

    public Set<KnowledgeSpace> getKnowledgeSpaces() {
        return knowledgeSpaces;
    }

    public void setKnowledgeSpaces(Set<KnowledgeSpace> knowledgeSpaces) {
        this.knowledgeSpaces = knowledgeSpaces;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", domainProblems=" + domainProblems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Domain)) return false;
        Domain domain = (Domain) o;
        return Objects.equals(getId(), domain.getId());
    }

}
