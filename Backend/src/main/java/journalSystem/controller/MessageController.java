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
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class MessageController {

    @Autowired
    private MessageServiceClient messageServiceClient;

    private final IUserService userService;

    @Autowired
    public MessageController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public List<MessageDTO> getAllMessages() {
        List<MessageDTO> messageDTOs = messageServiceClient.getAllMessages();
        if (messageDTOs != null) {
            return messageDTOs;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable int id) {
        MessageDTO messageDTO = messageServiceClient.getMessageById(id);
        if (messageDTO != null) {
            return ResponseEntity.ok(messageDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO) {
        User sender = userService.getUserByEmail(messageDTO.getSender());
        User receiver = userService.getUserByEmail(messageDTO.getReceiver());

        if (sender == null || receiver == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(messageServiceClient.createMessage(messageDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageDTO> updateMessage(@PathVariable int id, @RequestBody MessageDTO messageDTO) {
        User sender = userService.getUserByEmail(messageDTO.getSender());
        User receiver = userService.getUserByEmail(messageDTO.getReceiver());

        if (sender == null || receiver == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(messageServiceClient.updateMessage(id, messageDTO));
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<MessageDTO> readMessage(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(messageServiceClient.readMessage(id));
    }

    @GetMapping("/getAllReceived/{userId}")
    public List<MessageDTO> getAllReceived(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        if (user == null) throw new IllegalArgumentException("No User With Id: " + userId);

        List<MessageDTO> messageDTOs = messageServiceClient.getAllReceived(user.getEmail());

        if (messageDTOs != null) {
            return messageDTOs;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/getAllUnread/{userId}")
    public List<MessageDTO> getAllUnread(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        if (user == null) throw new IllegalArgumentException("No User With Id: " + userId);

        List<MessageDTO> messageDTOs = messageServiceClient.getAllUnread(user.getEmail());

        if (messageDTOs != null) {
            return messageDTOs;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/getAllSent/{userId}")
    public List<MessageDTO> getAllSent(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        if (user == null) throw new IllegalArgumentException("No User With Id: " + userId);

        List<MessageDTO> messageDTOs = messageServiceClient.getAllSent(user.getEmail());

        if (messageDTOs != null) {
            return messageDTOs;
        } else {
            return new ArrayList<>();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int id) {
        if (messageServiceClient.getMessageById(id) != null) {
            messageServiceClient.deleteMessage(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}