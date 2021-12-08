package eobrazovanje.dto;

public class KstLibParamsDTO {

    private int items;
    private int size;
    private double ce;
    private double lg;
    private double delta;

    public KstLibParamsDTO() {
    }

    public KstLibParamsDTO(int items, int size, double ce, double lg, double delta) {
        this.items = items;
        this.size = size;
        this.ce = ce;
        this.lg = lg;
        this.delta = delta;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getCe() {
        return ce;
    }

    public void setCe(double ce) {
        this.ce = ce;
    }

    public double getLg() {
        return lg;
    }

    public void setLg(double lg) {
        this.lg = lg;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }
}
