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
@Getter @Setter
public class KnowledgeSpace {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "knowledgeSpace", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("links")
    private Set<Link> links = new HashSet<>();

    @ManyToOne( fetch = FetchType.LAZY)
    @JsonBackReference("knowledgeSpaces")
    private Domain domain;

    @OneToMany(mappedBy = "knowledgeSpace", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("states")
    private Set<State> states = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private KnowledgeSpaceType type;

    public KnowledgeSpace() {
    }

    public KnowledgeSpace(Long id, String title, Set<Link> links, Domain domain) {
        this.id = id;
        this.title = title;
        this.links = links;
        this.domain = domain;
    }


    @Override
    public String toString() {
        return "KnowledgeSpace{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type.toString() + '\'' +
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
