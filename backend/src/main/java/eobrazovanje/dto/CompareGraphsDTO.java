package eobrazovanje.dto;

import eobrazovanje.model.KnowledgeSpace;

import java.util.ArrayList;
import java.util.List;

public class CompareGraphsDTO {
    private GraphDTO ks1;
    private GraphDTO ks2;
    private List<LinkDTO> links = new ArrayList<>();

    public CompareGraphsDTO() {
    }

    public CompareGraphsDTO(GraphDTO ks1, GraphDTO ks2, List<LinkDTO> links) {
        this.ks1 = ks1;
        this.ks2 = ks2;
        this.links = links;
    }

    public GraphDTO getKs1() {
        return ks1;
    }

    public void setKs1(GraphDTO ks1) {
        this.ks1 = ks1;
    }

    public GraphDTO getKs2() {
        return ks2;
    }

    public void setKs2(GraphDTO ks2) {
        this.ks2 = ks2;
    }

    public List<LinkDTO> getLinks() {
        return links;
    }

    public void setLinks(List<LinkDTO> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "CompareGraphsDTO{" +
                "ks1=" + ks1 +
                ", ks2=" + ks2 +
                ", links=" + links +
                '}';
    }
}
