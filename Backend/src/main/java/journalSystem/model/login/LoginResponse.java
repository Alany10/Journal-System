package journalSystem.model.login;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
    private String message;
    private String token; // Token från Keycloak

    public LoginResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public LoginResponse(String message) {
        this.message = message;
    }
}
