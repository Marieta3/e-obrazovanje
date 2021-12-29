package eobrazovanje.dto;

import java.util.ArrayList;

public class KstLibResponseDTO {
    private ArrayList<ArrayList<Integer>> implications;
    private ArrayList<ArrayList<Integer>> paths;

    public KstLibResponseDTO() {
    }

    public KstLibResponseDTO(ArrayList<ArrayList<Integer>> implications, ArrayList<ArrayList<Integer>> paths) {
        this.implications = implications;
        this.paths = paths;
    }

    public ArrayList<ArrayList<Integer>> getImplications() {
        return implications;
    }

    public void setImplications(ArrayList<ArrayList<Integer>> implications) {
        this.implications = implications;
    }

    public ArrayList<ArrayList<Integer>> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<ArrayList<Integer>> paths) {
        this.paths = paths;
    }

    @Override
    public String toString() {
        return "KstLibResponseDTO{" +
                "implications=" + implications +
                ", paths=" + paths +
                '}';
    }
}
