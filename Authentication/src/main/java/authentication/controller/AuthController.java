package authentication.controller;

import authentication.model.*;
import authentication.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"https://backend-service:8080", "https://localhost:8001", "https://localhost:30001"}, allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthUserService authUserService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestParam String email, @RequestParam String password) {
        try {
            // Logga in via Keycloak och hämta JWT-token
            String token = authUserService.login(email, password);

            return ResponseEntity.ok(new LoginResponse("Login successful", token)); // Skickar en pseudo-id
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Wrong password"));
        }
    }

    @GetMapping("/validate")
    public boolean validateToken(@RequestHeader("Authorization") String token) {
        try {
            return authUserService.validateToken(token);
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        try {
            authUserService.logout(token);
            return ResponseEntity.status(HttpStatus.OK).body("Logged out successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error logging out: " + e.getMessage());
        }
    }

    // Skapa användare via Keycloak Admin API
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestParam String email, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String password) {
        try {
            // Skapa användare genom AuthUserService
            authUserService.createUser(email, firstName, lastName, password);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }
}
