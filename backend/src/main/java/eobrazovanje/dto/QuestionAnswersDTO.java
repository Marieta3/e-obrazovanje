package eobrazovanje.dto;

import java.util.ArrayList;

public class QuestionAnswersDTO {

    private Long questionID;
    private ArrayList<Long> answerIDs;

    public QuestionAnswersDTO() {
    }

    public QuestionAnswersDTO(Long questionID, ArrayList<Long> answerIDs) {
        this.questionID = questionID;
        this.answerIDs = answerIDs;
    }

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    public ArrayList<Long> getAnswerIDs() {
        return answerIDs;
    }

    public void setAnswerIDs(ArrayList<Long> answerIDs) {
        this.answerIDs = answerIDs;
    }
}
