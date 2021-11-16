package eobrazovanje.dto;

import java.util.List;

public class KnowledgeSpaceDTO {

    private List<DomainProblemDTO> nodes;

    private List<LinkDTO> links;

    private String title;

    public KnowledgeSpaceDTO() {
    }

    public KnowledgeSpaceDTO(List<DomainProblemDTO> nodes, List<LinkDTO> links, String title) {
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
