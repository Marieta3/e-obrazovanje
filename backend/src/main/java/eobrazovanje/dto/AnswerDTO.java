package eobrazovanje.dto;

public class AnswerDTO {
    private Long id;
    private String text;
    private String imagePath;
    private Boolean correct;

    public AnswerDTO(Long id, String text, String imagePath, Boolean correct) {
        this.id = id;
        this.text = text;
        this.imagePath = imagePath;
        this.correct = correct;
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

    public AnswerDTO() {
    }
}
