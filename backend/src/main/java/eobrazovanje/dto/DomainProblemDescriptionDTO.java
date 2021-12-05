package eobrazovanje.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import eobrazovanje.model.DomainProblem;
import eobrazovanje.model.KnowledgeSpaceNode;
import lombok.Getter;
import lombok.Setter;

public class DomainProblemDescriptionDTO {

    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String title, description;

    public DomainProblemDescriptionDTO() {
    }

    public DomainProblemDescriptionDTO(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public DomainProblemDescriptionDTO(DomainProblem domainProblem){
        this.id = domainProblem.getId();
        this.title = domainProblem.getTitle();
        this.description = domainProblem.getDescription();
    }
}
