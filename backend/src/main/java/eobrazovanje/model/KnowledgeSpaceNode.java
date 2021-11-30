package eobrazovanje.model;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "domain_problem_id", nullable = false)
    @Getter @Setter
    private DomainProblem node;

    @Embedded
    @Getter @Setter
    @AttributeOverrides({
            @AttributeOverride( name = "x", column = @Column(name = "coordinate_x")),
            @AttributeOverride( name = "y", column = @Column(name = "coordinate_y"))})
    private Coordinates coordinates;
    @Embedded
    @Getter @Setter
    private Size size;
}
