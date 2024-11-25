package authentication.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String email;
    private String password;
    private String role;

    public UserDTO(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}