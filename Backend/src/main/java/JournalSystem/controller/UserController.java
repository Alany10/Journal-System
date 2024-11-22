package JournalSystem.controller;

import JournalSystem.model.User;
import JournalSystem.model.Role;
import JournalSystem.model.login.LoginRequest;
import JournalSystem.model.login.LoginResponse;
import JournalSystem.service.interfaces.IUserService;
import JournalSystem.viewModel.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class UserController {

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

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getEmail() == null ||
                userDTO.getName() == null ||
                userDTO.getPassword() == null ||
                userDTO.getPhoneNr() == null ||
                userDTO.getRole() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();


        User user = new User(userDTO.getEmail(),
                    userDTO.getName(),
                    userDTO.getPassword(),
                    userDTO.getPhoneNr(),
                    Role.valueOf(userDTO.getRole().toUpperCase())
                    );

        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(createdUser));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        if (userDTO.getEmail() == null ||
                userDTO.getName() == null ||
                userDTO.getPassword() == null ||
                userDTO.getPhoneNr() == null ||
                userDTO.getRole() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        User updatedUser = userService.updateUser(id, new User(
                id,
                userDTO.getEmail(),
                userDTO.getName(),
                userDTO.getPassword(),
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        boolean loginSuccessful = userService.verifyLogin(request.getEmail(), request.getPassword(), Role.valueOf(request.getRole().toUpperCase()));
        if (loginSuccessful) {
            int practitionerId = userService.getIdByEmail(request.getEmail()); // Assuming you have a method to retrieve the ID
            return ResponseEntity.ok(new LoginResponse("Login successful", practitionerId));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid email or password"));
        }
    }
}
