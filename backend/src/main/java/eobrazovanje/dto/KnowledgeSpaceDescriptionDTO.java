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

    public KnowledgeSpaceDescriptionDTO() {
    }

    public KnowledgeSpaceDescriptionDTO(KnowledgeSpace knowledgeSpace){
        this.id = knowledgeSpace.getId();
        this.createdAt = knowledgeSpace.getCreatedAt();
        this.type = knowledgeSpace.getType().toString();
    }

    public KnowledgeSpaceDescriptionDTO(Long id, Date createdAt, String type) {
        this.id = id;
        this.createdAt = createdAt;
        this.type = type;
    }
}
