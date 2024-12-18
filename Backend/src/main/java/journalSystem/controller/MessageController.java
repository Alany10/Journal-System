package journalSystem.controller;

import journalSystem.service.MessageServiceClient;
import journalSystem.model.*;
import journalSystem.service.interfaces.IUserService;
import journalSystem.viewModel.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = {"https://frontend-service:5173", "https://localhost:8000", "https://localhost:30000"}, allowedHeaders = "*")
public class MessageController {

    private final MessageServiceClient messageServiceClient;
    private final IUserService userService;
    private final UserController userController;

    @Autowired
    public MessageController(IUserService userService, MessageServiceClient messageServiceClient, UserController userController) {
        this.userService = userService;
        this.messageServiceClient = messageServiceClient;
        this.userController = userController;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MessageDTO>> getAllMessages(@RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        List<MessageDTO> messageDTOs = messageServiceClient.getAllMessages();
        if (messageDTOs != null) {
            return ResponseEntity.ok(messageDTOs);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        MessageDTO messageDTO = messageServiceClient.getMessageById(id);
        if (messageDTO != null) {
            return ResponseEntity.ok(messageDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createMessage(@RequestBody MessageDTO messageDTO, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        User sender = userService.getUserByEmail(messageDTO.getSender());
        User receiver = userService.getUserByEmail(messageDTO.getReceiver());

        if (sender == null || receiver == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Created message");
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<String> readMessage(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        messageServiceClient.readMessage(id);
        return ResponseEntity.status(HttpStatus.OK).body("Message is now read");
    }

    @GetMapping("/getAllReceived/{userId}")
    public ResponseEntity<List<MessageDTO>> getAllReceived(@PathVariable int userId, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        User user = userService.getUserById(userId);
        if (user == null) throw new IllegalArgumentException("No User With id: " + userId);

        List<MessageDTO> messageDTOs = messageServiceClient.getAllReceived(user.getEmail());

        if (messageDTOs != null) {
            return ResponseEntity.ok(messageDTOs);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/getAllUnread/{userId}")
    public ResponseEntity<List<MessageDTO>> getAllUnread(@PathVariable int userId, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        User user = userService.getUserById(userId);
        if (user == null) throw new IllegalArgumentException("No User With id: " + userId);

        List<MessageDTO> messageDTOs = messageServiceClient.getAllUnread(user.getEmail());

        if (messageDTOs != null) {
            return ResponseEntity.ok(messageDTOs);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/getAllSent/{userId}")
    public ResponseEntity<List<MessageDTO>> getAllSent(@PathVariable int userId, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        User user = userService.getUserById(userId);
        if (user == null) throw new IllegalArgumentException("No User With email: " + userId);

        List<MessageDTO> messageDTOs = messageServiceClient.getAllSent(user.getEmail());

        if (messageDTOs != null) {
            return ResponseEntity.ok(messageDTOs);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (messageServiceClient.getMessageById(id) != null) {
            messageServiceClient.deleteMessage(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}