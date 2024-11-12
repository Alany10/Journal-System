package JournalSystem.model.login;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
