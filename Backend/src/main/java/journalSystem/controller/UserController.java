package journalSystem.controller;

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
@CrossOrigin(origins = {"http://frontend-service:5173", "http://localhost:5173", "http://localhost:30000"}, allowedHeaders = "*")
public class UserController {

    @Autowired
    private AuthServiceClient authServiceClient;

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users != null) {
            List<UserDTO> userDTOS = new ArrayList<>();
            for (User user : users){
                userDTOS.add(Mapper.convertToDTO(user));
            }
            return userDTOS;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/getAllDoctors")
    public List<UserDTO> getAllDoctors() {
        List<User> users = userService.getAllDoctors();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(Mapper.convertToDTO(user));
        }
        return userDTOS;
    }

    @GetMapping("/getAllPatients")
    public List<UserDTO> getAllPatients() {
        List<User> users = userService.getAllPatients();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(Mapper.convertToDTO(user));
        }
        return userDTOS;
    }

    @GetMapping("/getAllOthers")
    public List<UserDTO> getAllOthers() {
        List<User> users = userService.getAllOthers();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(Mapper.convertToDTO(user));
        }
        return userDTOS;
    }

    @GetMapping("/getAllPractitioners")
    public List<UserDTO> getAllPractitioners() {
        List<User> users = userService.getAllPractitioners();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(Mapper.convertToDTO(user));
        }
        return userDTOS;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(Mapper.convertToDTO(user));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(Mapper.convertToDTO(user));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getIdByEmail/{email}")
    public int getUserIdByEmail(@PathVariable String email) {
        return userService.getIdByEmail(email);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getEmail() == null ||
                userDTO.getFirstName() == null ||
                userDTO.getLastName() == null ||
                userDTO.getPassword() == null ||
                userDTO.getPhoneNr() == null ||
                userDTO.getRole() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        User user = new User(userDTO.getEmail(),
                    userDTO.getFirstName(),
                    userDTO.getLastName(),
                    userDTO.getPhoneNr(),
                    Role.valueOf(userDTO.getRole().toUpperCase())
                    );

        authServiceClient.createUser(userDTO);
        User createdUser = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(createdUser));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        if (id >= 0 && userService.existsById(id)) {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = userService.getUserByEmailAndRole(request.getEmail(), Role.valueOf(request.getRole().toUpperCase()));

        if (user != null) return authServiceClient.login(request.getEmail(), request.getPassword());

        return new LoginResponse("User not found");
    }
}
