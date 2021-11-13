package eobrazovanje.dto;

import lombok.Getter;
import lombok.Setter;

public class CourseDTO {

    @Getter @Setter
    private Long id, teacherId;

    @Getter @Setter
    private String name, description;

    public CourseDTO(Long id, String name, String description, Long teacherId) {
        this.name = name;
        this.description = description;
        this.teacherId = teacherId;
        this.id = id;
    }

    public CourseDTO() {
    }
}
