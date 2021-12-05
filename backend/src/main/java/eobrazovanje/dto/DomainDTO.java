package eobrazovanje.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import eobrazovanje.model.Domain;
import eobrazovanje.model.DomainProblem;
import eobrazovanje.util.Converter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class DomainDTO {
    @Getter @Setter
    private String title;
    @Getter @Setter
    @JsonProperty("domainProblems")
    private List<DomainProblemDescriptionDTO> domainProblemDTOList;

    public DomainDTO() {
    }

    public DomainDTO(String title, List<DomainProblemDescriptionDTO> domainProblemDTOList) {
        this.title = title;
        this.domainProblemDTOList = domainProblemDTOList;
    }

    public DomainDTO(Domain domain){
        this.title = domain.getTitle();
        List<DomainProblem> domainProblems = List.copyOf(domain.getDomainProblems());
        this.domainProblemDTOList = Converter.domainProblemsToDomainProblemDescriptionDTOList(domainProblems);
    }
}
