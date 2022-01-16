package eobrazovanje.dto;

import lombok.Getter;
import lombok.Setter;

public class CourseDTO {

    @Getter @Setter
    private Long id, teacherId;

    @Getter @Setter
    private String name, description,identifier;

    public CourseDTO(Long id, String name, String description, Long teacherId) {
        this.name = name;
        this.description = description;
        this.teacherId = teacherId;
        this.id = id;
    }

    public CourseDTO(Long id, String name, String description,Long teacherId, String identifier) {
        this.id = id;
        this.teacherId = teacherId;
        this.name = name;
        this.description = description;
        this.identifier = identifier;
    }

    public CourseDTO() {
    }
}
