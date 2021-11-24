package eobrazovanje.dto;


import java.util.ArrayList;

public class QuestionDTO {
    private String text;
    private boolean randomized;
    private ArrayList<AnswerDTO> answers;
    private Long domainProblemId;

    public QuestionDTO() {
    }

    public QuestionDTO(String text, boolean randomized, ArrayList<AnswerDTO> answers, Long domainProblemId) {
        this.text = text;
        this.randomized = randomized;
        this.answers = answers;
        this.domainProblemId = domainProblemId;
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

    public ArrayList<AnswerDTO> getAnswers() {
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
