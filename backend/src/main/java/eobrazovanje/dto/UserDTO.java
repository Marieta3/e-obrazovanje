package eobrazovanje.dto;

import lombok.Getter;
import lombok.Setter;

public class UserDTO {
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String username, firstName, lastName;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
