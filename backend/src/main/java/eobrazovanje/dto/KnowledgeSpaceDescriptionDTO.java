package eobrazovanje.dto;

import eobrazovanje.model.KnowledgeSpace;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class KnowledgeSpaceDescriptionDTO {

    @Getter @Setter
    private Long id;
    @Getter @Setter
    private Date createdAt;
    @Getter @Setter
    private String type;
    @Getter @Setter
    private boolean isActive;

    public KnowledgeSpaceDescriptionDTO() {
    }

    public KnowledgeSpaceDescriptionDTO(KnowledgeSpace knowledgeSpace){
        this.id = knowledgeSpace.getId();
        this.createdAt = knowledgeSpace.getCreatedAt();
        this.type = "TEST";
        this.isActive = true;
    }

    public KnowledgeSpaceDescriptionDTO(Long id, Date createdAt, String type, boolean isActive) {
        this.id = id;
        this.createdAt = createdAt;
        this.type = type;
        this.isActive = isActive;
    }
}
