package eobrazovanje.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
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

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Getter @Setter
    private Date createdAt = new Date();

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "knowledgeSpace", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("links")
    private Set<Link> links = new HashSet<>();

    @ManyToOne( fetch = FetchType.LAZY)
    @JsonBackReference("knowledgeSpaces")
    private Domain domain;

    public KnowledgeSpace() {
    }

    public KnowledgeSpace(Long id, String title, Set<Link> links, Domain domain) {
        this.id = id;
        this.title = title;
        this.links = links;
        this.domain = domain;
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

    public Set<Link> getLinks() {
        return links;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "KnowledgeSpace{" +
                "id=" + id +
                ", title='" + title + '\'' +
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

}
