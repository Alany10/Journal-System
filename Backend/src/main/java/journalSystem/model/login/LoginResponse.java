package journalSystem.model.login;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
    private String message;
    private int id;

    public LoginResponse(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public LoginResponse(String message) {
        this.message = message;
    }

}
