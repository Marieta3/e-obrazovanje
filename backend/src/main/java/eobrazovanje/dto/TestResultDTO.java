package eobrazovanje.dto;

import java.util.ArrayList;
import java.util.Date;

public class TestResultDTO {

    private Date startTime;
    private Date endTime;
    private ArrayList<Long> answerIDs;

    public TestResultDTO() {
    }

    public TestResultDTO(Date startTime, Date endTime, ArrayList<Long> answerIDs) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.answerIDs = answerIDs;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ArrayList<Long> getAnswerIDs() {
        return answerIDs;
    }

    public void setAnswerIDs(ArrayList<Long> answerIDs) {
        this.answerIDs = answerIDs;
    }
}
