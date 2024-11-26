package journalSystem.service;

import journalSystem.model.Role;
import journalSystem.model.login.LoginRequest;
import journalSystem.model.login.LoginResponse;
import journalSystem.viewModel.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    private static final String AUTH_SERVICE_BASE_URL = "http://localhost:8081/auth"; // Anpassa URL:en

    public LoginResponse login(String email, String password, Role role) {
        String url = AUTH_SERVICE_BASE_URL + "/login";

        // Skapa LoginRequest-objekt
        LoginRequest loginRequest = new LoginRequest(email, password, role.toString());

        // Skicka POST-begäran
        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(url, loginRequest, LoginResponse.class);

        return response.getBody();
    }

    public UserDTO createUser(UserDTO userDTO) {
        String url = AUTH_SERVICE_BASE_URL + "/create";

        // Skicka POST-begäran
        ResponseEntity<UserDTO> response = restTemplate.postForEntity(url, userDTO, UserDTO.class);

        return response.getBody();
    }
}
