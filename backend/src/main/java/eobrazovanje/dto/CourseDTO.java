package eobrazovanje.dto;

public class CourseDTO {

    private String name;
    private String description;
    private Long teacherId;

    public CourseDTO(String name, String description, Long teacherId) {
        this.name = name;
        this.description = description;
        this.teacherId = teacherId;
    }

    public CourseDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
