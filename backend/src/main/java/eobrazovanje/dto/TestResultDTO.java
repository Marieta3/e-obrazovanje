package eobrazovanje.dto;

import java.util.ArrayList;
import java.util.Date;

public class TestResultDTO {

    private Date startTime;
    private Date endTime;
    private ArrayList<Long> answerIDs;
    private Long testID;

    public TestResultDTO() {
    }

    public TestResultDTO(Date startTime, Date endTime, ArrayList<Long> answerIDs, Long testID) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.answerIDs = answerIDs;
        this.testID = testID;
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

    public Long getTestID() {
        return testID;
    }

    public void setTestID(Long testID) {
        this.testID = testID;
    }
}
