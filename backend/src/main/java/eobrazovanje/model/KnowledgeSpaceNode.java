package eobrazovanje.model;

import com.fasterxml.jackson.databind.util.ClassUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "knowledge_space_node")
public class KnowledgeSpaceNode {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(name = "node", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @Getter @Setter
    private DomainProblem node;

    @Embedded
    @Getter @Setter
    private Coordinates coordinates;
    @Embedded
    @Getter @Setter
    private Size size;
}
