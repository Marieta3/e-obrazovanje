package eobrazovanje.dto;

import lombok.Getter;
import lombok.Setter;

public class TestDescriptionDTO {
    @Getter @Setter
    private String title;

    @Getter @Setter
    private Long testId, courseId;

    public TestDescriptionDTO() {
    }

    public TestDescriptionDTO(String title, Long testId, Long courseId) {
        this.title = title;
        this.testId = testId;
        this.courseId = courseId;
    }

}
