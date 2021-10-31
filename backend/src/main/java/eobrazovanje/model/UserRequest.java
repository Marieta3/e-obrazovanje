package eobrazovanje.model;

import lombok.Getter;
import lombok.Setter;

public class UserRequest {

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;
}
