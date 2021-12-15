package eobrazovanje.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
public class Coordinates {
    @Getter @Setter
    Double x;
    @Getter @Setter
    Double y;

    public Coordinates() {
    }

    public Coordinates(Double x, Double y) {
        this.x = x;
        this.y = y;
    }
}
