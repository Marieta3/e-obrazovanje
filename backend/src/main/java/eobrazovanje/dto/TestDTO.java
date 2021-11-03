package eobrazovanje.dto;

import java.util.ArrayList;

public class TestDTO {

    private String title;
    private Long courseId;
    private ArrayList<QuestionDTO> questions;

    public TestDTO(String title, Long courseId, ArrayList<QuestionDTO> questions) {
        this.title = title;
        this.courseId = courseId;
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public ArrayList<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuestionDTO> questions) {
        this.questions = questions;
    }

    public TestDTO() {
    }
}
