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
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthUserService authUserService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        AuthUser user = authUserService.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (request.getPassword().equals(user.getPassword()) && request.getRole().toUpperCase().equals(user.getRole().toString())) {
            return ResponseEntity.ok(new LoginResponse("Login successful", user.getId()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid email or password"));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getEmail() == null ||
                userDTO.getPassword() == null ||
                userDTO.getRole() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        AuthUser user = new AuthUser(
                userDTO.getEmail(),
                userDTO.getPassword(),
                Role.valueOf(userDTO.getRole().toUpperCase())
        );

        authUserService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }
}
