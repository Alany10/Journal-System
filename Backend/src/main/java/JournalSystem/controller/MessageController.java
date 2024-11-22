package JournalSystem.controller;

import JournalSystem.model.*;
import JournalSystem.service.interfaces.IMessageService;
import JournalSystem.service.interfaces.IUserService;
import JournalSystem.viewModel.MessageDTO;
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
    private final IMessageService messageService;
    private final IUserService userService;

    @Autowired
    public MessageController(IMessageService messageService, IUserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public List<MessageDTO> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        if (messages != null) {
            List<MessageDTO> messageDTOs = new ArrayList<>();
            for (Message message: messages){
                messageDTOs.add(Mapper.convertToDTO(message));
            }
            return messageDTOs;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable int id) {
        Message message = messageService.getMessageById(id);
        if (message != null) {
            return ResponseEntity.ok(Mapper.convertToDTO(message));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO) {
        if (messageDTO.getTitle() == null ||
                messageDTO.getText() == null ||
                messageDTO.getPatientId() < 0 ||
                messageDTO.getPractitionerId() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        System.out.println(messageDTO.getPatientId() + " " + messageDTO.getPractitionerId());
        User patient = userService.getPatientById(messageDTO.getPatientId());
        User practitioner = userService.getPractitionerById(messageDTO.getPractitionerId());

        if (patient == null || practitioner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Message message = new Message(messageDTO.getTitle(), messageDTO.getText(), Role.valueOf(messageDTO.getSender().toUpperCase()), patient, practitioner);
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(createdMessage));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageDTO> updateMessage(@PathVariable int id, @RequestBody MessageDTO messageDTO) {
        if (messageDTO.getTitle() == null ||
                messageDTO.getText() == null ||
                messageDTO.getPatientId() < 0 ||
                messageDTO.getPractitionerId() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User patient = userService.getPatientById(messageDTO.getPatientId());
        User user = userService.getPractitionerById(messageDTO.getPractitionerId());

        if (patient == null || user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Message updatedMessage = messageService.updateMessage(id, new Message(
                id,
                messageDTO.getTitle(),
                messageDTO.getText(),
                messageDTO.getDateTime(),
                messageDTO.getIsRead(),
                Role.valueOf(messageDTO.getSender().toUpperCase()),
                patient,
                user
        ));

        if (updatedMessage != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(updatedMessage));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<MessageDTO> readMessage(@PathVariable int id) {
        Message message = messageService.getMessageById(id);

        User patient = userService.getPatientById(message.getPatient().getId());
        User practitioner = userService.getPractitionerById(message.getPractitioner().getId());

        if (patient == null || practitioner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Message updatedMessage = messageService.updateMessage(id, new Message(
                id,
                message.getTitle(),
                message.getText(),
                message.getDateTime(),
                true,
                Role.valueOf(message.getSender().toString()),
                patient,
                practitioner
        ));

        if (updatedMessage != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(updatedMessage));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getAllReceived/{userId}")
    public List<MessageDTO> getAllReceived(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        if (user == null) throw new IllegalArgumentException("No Patient With Id: " + userId);

        List<Message> messages = messageService.getAllReceivedById(user.getId(), user.getRole());

        if (messages != null) {
            List<MessageDTO> messageDTOs = new ArrayList<>();
            for (Message message : messages){
                messageDTOs.add(Mapper.convertToDTO(message));
            }
            return messageDTOs;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/getAllUnread/{userId}")
    public List<MessageDTO> getAllUnread(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        if (user == null) throw new IllegalArgumentException("No Patient With Id: " + userId);

        List<Message> messages = messageService.getAllUnreadById(user.getId(), user.getRole());

        if (messages != null) {
            List<MessageDTO> messageDTOs = new ArrayList<>();
            for (Message message : messages){
                messageDTOs.add(Mapper.convertToDTO(message));
            }
            return messageDTOs;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/getAllSent/{userId}")
    public List<MessageDTO> getAllSent(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        if (user == null) throw new IllegalArgumentException("No User With Id: " + userId);

        List<Message> messages = messageService.getAllSentById(user.getId(), user.getRole());

        if (messages != null) {
            List<MessageDTO> messageDTOs = new ArrayList<>();
            for (Message message : messages){
                messageDTOs.add(Mapper.convertToDTO(message));
            }
            return messageDTOs;
        } else {
            return new ArrayList<>();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int id) {
        if (id >= 0 && messageService.existsById(id)) {
            messageService.deleteMessage(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}