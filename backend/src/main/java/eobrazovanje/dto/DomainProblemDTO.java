package eobrazovanje.dto;

import eobrazovanje.model.Coordinates;
import eobrazovanje.model.Size;
import lombok.Getter;
import lombok.Setter;

public class DomainProblemDTO {
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String title;
    @Getter @Setter
    private Size size;
    @Getter @Setter
    private Coordinates coordinates;
    @Getter @Setter
    private Ports portsIn = new Ports("in");
    @Getter @Setter
    private Ports portsOut = new Ports("out");
    @Getter @Setter
    private Data data;


    public DomainProblemDTO() {
    }

    public DomainProblemDTO(Long id, String title, Size size, Coordinates coordinates, String description, Long domainProblemId) {
        this.id = id;
        this.title = title;
        this.size = size;
        this.coordinates = coordinates;
        this.data = new Data(description,domainProblemId);
    }

    @Override
    public String toString() {
        return "DomainProblemDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", size=" + size +
                ", coordinates=" + coordinates.toString() +
                ", portsIn=" + portsIn +
                ", portsOut=" + portsOut +
                '}';
    }

    public class Ports{
        @Getter @Setter
        private String port;

        public Ports() {
        }

        public Ports(String port) {
            this.port = port;
        }
    }

    public class Data{
        @Getter @Setter
        private String description;
        @Getter @Setter
        private Long domainProblemId;

        public Data() {
        }

        public Data(String description, Long domainProblemId) {
            this.description = description;
            this.domainProblemId = domainProblemId;
        }
    }

}

