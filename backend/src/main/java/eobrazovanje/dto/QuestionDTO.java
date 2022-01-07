package eobrazovanje.dto;


import eobrazovanje.model.Question;
import eobrazovanje.util.Converter;

import java.util.ArrayList;
import java.util.List;

public class QuestionDTO {
    private String text;
    private boolean randomized;
    private List<AnswerDTO> answers;
    private Long domainProblemId;

    public QuestionDTO() {
    }

    public QuestionDTO(String text, boolean randomized, List<AnswerDTO> answers, Long domainProblemId) {
        this.text = text;
        this.randomized = randomized;
        this.answers = answers;
        this.domainProblemId = domainProblemId;
    }

    public QuestionDTO(Question question, boolean hideCorrectAnswer){
        this(question.getText(), question.isRandomize(), Converter.convert(new ArrayList<>(question.getAnswers()), hideCorrectAnswer),question.getDomainProblem().getId());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getRandomized() {
        return randomized;
    }

    public void setRandomized(boolean randomized) {
        this.randomized = randomized;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnswerDTO> answers) {
        this.answers = answers;
    }

    public Long getDomainProblemId() {
        return domainProblemId;
    }

    public void setDomainProblemId(Long domainProblemId) {
        this.domainProblemId = domainProblemId;
    }
}
