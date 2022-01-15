package eobrazovanje.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class TestAnswerDTO {
    private Long testId;
    private Long testResultId;
    private List<Long> answerIds;

}
