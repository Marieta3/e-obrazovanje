package eobrazovanje.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import eobrazovanje.dto.DomainProblemDTO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "knowledge_spaces")
public class KnowledgeSpace {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "knowledgeSpace", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("domainProblems")
    private Set<DomainProblem> domainProblems = new HashSet<>();

    @OneToMany(mappedBy = "knowledgeSpace", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("links")
    private Set<Link> links = new HashSet<>();

    public KnowledgeSpace() {
    }

    public KnowledgeSpace(Long id, String title, Set<DomainProblem> domainProblems, Set<Link> links) {
        this.id = id;
        this.title = title;
        this.domainProblems = domainProblems;
        this.links = links;
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

    public Set<DomainProblem> getDomainProblems() {
        return domainProblems;
    }

    public void setDomainProblems(Set<DomainProblem> domainProblems) {
        this.domainProblems = domainProblems;
    }

    public Set<Link> getLinks() {
        return links;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "KnowledgeSpace{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", domainProblems=" + domainProblems +
                ", links=" + links +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KnowledgeSpace)) return false;
        KnowledgeSpace that = (KnowledgeSpace) o;
        return Objects.equals(getId(), that.getId());
    }


    public void addDomainProblem(DomainProblem dp){
        domainProblems.add(dp);
    }
}
