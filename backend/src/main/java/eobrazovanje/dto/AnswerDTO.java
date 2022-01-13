package eobrazovanje.dto;

import eobrazovanje.model.Answer;

public class AnswerDTO {
    private Long id;
    private String text;
    private String imagePath;
    private Boolean correct;
    private Integer accuracy;

    public AnswerDTO(Long id, String text, String imagePath, Boolean correct, Integer accuracy) {
        this.id = id;
        this.text = text;
        this.imagePath = imagePath;
        this.correct = correct;
        this.accuracy = accuracy;
    }

    public AnswerDTO(Answer answer, boolean hideIsCorrectData){
        this(answer.getId(),answer.getText(), answer.getImagePath(),null,null);
        if(!hideIsCorrectData){
            setCorrect(answer.isCorrect());
            setAccuracy(answer.getAccuracy());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public AnswerDTO() {
    }
}
