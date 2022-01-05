package eobrazovanje.dto;

import lombok.Getter;
import lombok.Setter;

public class TestAnswerDTO {
    @Getter @Setter
    private Long testId;

    @Getter @Setter
    private Long testResultId;

    @Getter @Setter
    private Long answerId;

    public TestAnswerDTO() {
    }

    public TestAnswerDTO(Long testId, Long testResultId, Long answerId) {
        this.testId = testId;
        this.testResultId = testResultId;
        this.answerId = answerId;
    }
}
