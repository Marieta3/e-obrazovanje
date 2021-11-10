package eobrazovanje.dto;

public class AnswerDTO {
    private String text;
    private String imagePath;
    private Boolean correct;

    public AnswerDTO(String text, String imagePath, Boolean correct) {
        this.text = text;
        this.imagePath = imagePath;
        this.correct = correct;
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

    public AnswerDTO() {
    }
}
