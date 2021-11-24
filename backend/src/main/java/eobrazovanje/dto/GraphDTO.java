package eobrazovanje.dto;

import java.util.ArrayList;
import java.util.List;

public class GraphDTO {

    private List<DomainProblemDTO> nodes = new ArrayList<>();

    private List<LinkDTO> links = new ArrayList<>();

    private String title;

    public GraphDTO() {
    }

    public GraphDTO(List<DomainProblemDTO> nodes, List<LinkDTO> links, String title) {
        this.nodes = nodes;
        this.links = links;
        this.title = title;
    }

    public List<DomainProblemDTO> getNodes() {
        return nodes;
    }

    public void setNodes(List<DomainProblemDTO> nodes) {
        this.nodes = nodes;
    }

    public List<LinkDTO> getLinks() {
        return links;
    }

    public void setLinks(List<LinkDTO> links) {
        this.links = links;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
