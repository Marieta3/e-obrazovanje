package eobrazovanje.dto;

import lombok.Getter;
import lombok.Setter;

public class LinkDTO {
    @Getter @Setter
    private String id;
    @Getter @Setter
    private String start_id;
    @Getter @Setter
    private String start_port="port";
    @Getter @Setter
    private String end_id;
    @Getter @Setter
    private String end_port="port";

    public LinkDTO() {
    }

    public LinkDTO(String id, String start_id, String start_port, String end_id, String end_port) {
        this.id = id;
        this.start_id = start_id;
        this.start_port = start_port;
        this.end_id = end_id;
        this.end_port = end_port;
    }
}
