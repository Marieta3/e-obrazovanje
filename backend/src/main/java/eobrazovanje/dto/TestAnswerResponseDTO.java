package eobrazovanje.dto;

import lombok.Getter;
import lombok.Setter;

public class TestAnswerResponseDTO {
    @Getter @Setter
    Long testResultId;
    @Getter @Setter
    QuestionDTO question;

    public TestAnswerResponseDTO() {
    }

    public TestAnswerResponseDTO(Long testResultId, QuestionDTO questionDTO) {
        this.testResultId = testResultId;
        this.question = questionDTO;
    }
}
