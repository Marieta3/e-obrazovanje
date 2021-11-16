package eobrazovanje.dto;

import lombok.Getter;
import lombok.Setter;

public class DomainProblemDTO {
    @Getter @Setter
    private String id;
    @Getter @Setter
    private String title;
    @Getter @Setter
    private Size size;
    @Getter @Setter
    private Coordinates coordinates;
    @Getter @Setter
    private Ports portsIn;
    @Getter @Setter
    private Ports portsOut;

    public DomainProblemDTO() {
    }

    public DomainProblemDTO(String id, String title, Size size, Coordinates coordinates, Ports portsIn, Ports portsOut) {
        this.id = id;
        this.title = title;
        this.size = size;
        this.coordinates = coordinates;
        this.portsIn = portsIn;
        this.portsOut = portsOut;
    }

    public class Coordinates{
        @Getter @Setter
        private Double x;
        @Getter @Setter
        private Double y;

        public Coordinates() {
        }

        public Coordinates(Double x, Double y) {
            this.x = x;
            this.y = y;
        }
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

    public class Size{
        @Getter @Setter
        private Double width;
        @Getter @Setter
        private Double height;

        public Size(Double width, Double height) {
            this.width = width;
            this.height = height;
        }

        public Size() {
        }
    }
}

