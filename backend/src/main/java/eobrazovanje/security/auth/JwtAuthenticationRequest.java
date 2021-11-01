package eobrazovanje.security.auth;

import lombok.Getter;
import lombok.Setter;

public class JwtAuthenticationRequest {
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
}
