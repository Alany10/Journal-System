package journalSystem.controller;

import jakarta.transaction.Transactional;
import journalSystem.service.AuthServiceClient;
import journalSystem.model.User;
import journalSystem.model.Role;
import journalSystem.model.login.LoginRequest;
import journalSystem.model.login.LoginResponse;
import journalSystem.service.interfaces.IUserService;
import journalSystem.viewModel.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"https://frontend-service:5173", "https://localhost:8000", "https://localhost:30000"}, allowedHeaders = "*")
public class UserController {

    private final AuthServiceClient authServiceClient;
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService, AuthServiceClient authServiceClient) {
        this.userService = userService;
        this.authServiceClient = authServiceClient;
    }

    @Transactional
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestHeader("Authorization") String token) {
        if (!validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(Mapper.convertToDTO(user));
        }
        return ResponseEntity.ok(userDTOS);
    }

    @Transactional
    @GetMapping("/getAllDoctors")
    public ResponseEntity<List<UserDTO>> getAllDoctors(@RequestHeader("Authorization") String token) {
        if (!validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        List<User> users = userService.getAllDoctors();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(Mapper.convertToDTO(user));
        }
        return ResponseEntity.ok(userDTOS);
    }

    @Transactional
    @GetMapping("/getAllPatients")
    public ResponseEntity<List<UserDTO>> getAllPatients(@RequestHeader("Authorization") String token) {
        if (!validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        List<User> users = userService.getAllPatients();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(Mapper.convertToDTO(user));
        }
        return ResponseEntity.ok(userDTOS);
    }

    @Transactional
    @GetMapping("/getAllOthers")
    public ResponseEntity<List<UserDTO>> getAllOthers(@RequestHeader("Authorization") String token) {
        if (!validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        List<User> users = userService.getAllOthers();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(Mapper.convertToDTO(user));
        }
        return ResponseEntity.ok(userDTOS);
    }

    @Transactional
    @GetMapping("/getAllPractitioners")
    public ResponseEntity<List<UserDTO>> getAllPractitioners(@RequestHeader("Authorization") String token) {
        if (!validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        List<User> users = userService.getAllPractitioners();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(Mapper.convertToDTO(user));
        }
        return ResponseEntity.ok(userDTOS);
    }

    @Transactional
    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (!validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Returnera 401 om token är ogiltig
        }

        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(Mapper.convertToDTO(user));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email, @RequestHeader("Authorization") String token) {
        if (!validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Returnera 401 om token är ogiltig
        }

        User user = userService.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(Mapper.convertToDTO(user));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @GetMapping("/getIdByEmail/{email}")
    public ResponseEntity<Integer> getUserIdByEmail(@PathVariable String email, @RequestHeader("Authorization") String token) {
        if (!validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Returnera 401 om token är ogiltig
        }

        return ResponseEntity.ok(userService.getIdByEmail(email));
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        // Kontrollera om alla nödvändiga fält finns i userDTO
        if (userDTO.getEmail() == null ||
                userDTO.getFirstName() == null ||
                userDTO.getLastName() == null ||
                userDTO.getPassword() == null ||
                userDTO.getPhoneNr() == null ||
                userDTO.getRole() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Skapa User-objekt
        User user = new User(userDTO.getEmail(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getPhoneNr(),
                Role.valueOf(userDTO.getRole().toUpperCase()));

        try {
            ResponseEntity<String> authResponse = authServiceClient.createUser(userDTO);

            // Om authResponse inte är OK, returnera ett fel
            if (!authResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error creating user in auth service: " + authResponse.getBody());
            }

        } catch (Exception e) {
            // Om det uppstår ett fel vid anropet till auth service
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error communicating with auth service: " + e.getMessage());
        }

        // Om allt gick bra, skapa användaren i den lokala tjänsten
        User createdUser = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User Created!");
    }

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO, @RequestHeader("Authorization") String token) {
        if (!validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Returnera 401 om token är ogiltig
        }

        if (userDTO.getEmail() == null ||
                userDTO.getFirstName() == null ||
                userDTO.getLastName() == null ||
                userDTO.getPassword() == null ||
                userDTO.getPhoneNr() == null ||
                userDTO.getRole() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        User updatedUser = userService.updateUser(id, new User(
                id,
                userDTO.getEmail(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getPhoneNr(),
                Role.valueOf(userDTO.getRole().toUpperCase())
        ));

        if (updatedUser != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (!validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Returnera 401 om token är ogiltig
        }

        if (id >= 0 && userService.existsById(id)) {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = userService.getUserByEmailAndRole(request.getEmail(), Role.valueOf(request.getRole().toUpperCase()));

        if (user != null) return authServiceClient.login(request.getEmail(), request.getPassword());

        return new LoginResponse("User not found");
    }

    @Transactional
    @GetMapping("/validate")
    public boolean validate(@RequestHeader("Authorization") String token) {
        return authServiceClient.validate(token);
    }
}
