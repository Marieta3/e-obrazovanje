package eobrazovanje.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
public class Size {
    @Getter @Setter
    private Double width;
    @Getter @Setter
    private Double height;

    public Size() {
    }

    public Size(Double width, Double height) {
        this.width = width;
        this.height = height;
    }
}
