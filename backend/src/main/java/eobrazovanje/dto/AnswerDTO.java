package eobrazovanje.dto;

public class AnswerDTO {
    private String text;
    private String imagePath;
    private boolean correct;

    public AnswerDTO(String text, String imagePath, boolean correct) {
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

    public boolean getCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public AnswerDTO() {
    }
}
