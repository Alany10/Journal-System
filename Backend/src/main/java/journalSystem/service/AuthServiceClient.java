package journalSystem.service;

import journalSystem.model.Role;
import journalSystem.model.login.LoginResponse;
import journalSystem.viewModel.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AuthServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    private static final String AUTH_SERVICE_BASE_URL = "http://localhost:8081/auth";

    public LoginResponse login(String email, String password) {
        // Bygg upp URL med query-parametrar
        String url = UriComponentsBuilder.fromHttpUrl(AUTH_SERVICE_BASE_URL + "/login")
                .queryParam("email", email)
                .queryParam("password", password)
                .toUriString();

        // Skicka POST utan en egentlig request body
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<LoginResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                LoginResponse.class
        );

        return response.getBody();
    }

    public ResponseEntity<String> createUser(UserDTO userDTO) {
        // Build the URL with query parameters
        String url = UriComponentsBuilder.fromHttpUrl(AUTH_SERVICE_BASE_URL + "/create")
                .queryParam("email", userDTO.getEmail())
                .queryParam("firstName", userDTO.getFirstName())
                .queryParam("lastName", userDTO.getLastName())
                .queryParam("password", userDTO.getPassword())
                .toUriString();

        // Set the headers (no body in the request)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create an HttpEntity with the headers (empty body in the POST request)
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            // Send the POST request using RestTemplate
            ResponseEntity<LoginResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    LoginResponse.class
            );

            // Return a response based on the result
            if (response.getStatusCode() == HttpStatus.CREATED) {
                // If the user was created successfully, return a success message
                return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
            } else {
                // Handle other responses (e.g., failure cases)
                return ResponseEntity.status(response.getStatusCode()).body("Failed to create user");
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., connection issues)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }

}
