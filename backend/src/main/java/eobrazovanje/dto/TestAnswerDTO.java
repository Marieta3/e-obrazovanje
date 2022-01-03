package eobrazovanje.dto;

import lombok.Getter;
import lombok.Setter;

public class TestAnswerDTO {
    @Getter @Setter
    private Long TestId;

    @Getter @Setter
    private Long TestResultId;

    @Getter @Setter
    private Long answerId;

    public TestAnswerDTO() {
    }

    public TestAnswerDTO(Long testId, Long testResultId, Long answerId) {
        TestId = testId;
        TestResultId = testResultId;
        this.answerId = answerId;
    }
}
