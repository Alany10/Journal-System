package message.controller;

import message.model.Message;
import message.model.MessageDTO;
import message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = {"https://backend-service:8080", "https://localhost:8001", "https://localhost:30001"}, allowedHeaders = "*")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/getAll")
    public List<MessageDTO> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        if (messages != null) {
            List<MessageDTO> messageDTOs = new ArrayList<>();
            for (Message message : messages) {
                messageDTOs.add(convertToDTO(message));
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
            return ResponseEntity.ok(convertToDTO(message));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO) {
        if (messageDTO.getTitle() == null ||
                messageDTO.getText() == null ||
                messageDTO.getSender() == null ||
                messageDTO.getReceiver() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Message message = new Message(messageDTO.getTitle(), messageDTO.getText(), messageDTO.getSender(), messageDTO.getReceiver());
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdMessage));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageDTO> updateMessage(@PathVariable int id, @RequestBody MessageDTO messageDTO) {
        if (messageDTO.getTitle() == null ||
                messageDTO.getText() == null ||
                messageDTO.getSender() == null ||
                messageDTO.getReceiver() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Message updatedMessage = messageService.updateMessage(id, new Message(
                id,
                messageDTO.getTitle(),
                messageDTO.getText(),
                messageDTO.getDateTime(),
                messageDTO.getIsRead(),
                messageDTO.getSender(),
                messageDTO.getReceiver()
        ));

        if (updatedMessage != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(updatedMessage));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<MessageDTO> readMessage(@PathVariable int id) {
        Message message = messageService.getMessageById(id);

        Message updatedMessage = messageService.updateMessage(id, new Message(
                id,
                message.getTitle(),
                message.getText(),
                message.getDateTime(),
                true,
                message.getSender(),
                message.getReceiver()
        ));

        if (updatedMessage != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(updatedMessage));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getAllReceived/{email}")
    public List<MessageDTO> getAllReceived(@PathVariable String email) {
        List<Message> messages = messageService.getAllReceivedByEmail(email);

        if (messages != null) {
            List<MessageDTO> messageDTOs = new ArrayList<>();
            for (Message message : messages) {
                messageDTOs.add(convertToDTO(message));
            }
            return messageDTOs;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/getAllUnread/{email}")
    public List<MessageDTO> getAllUnread(@PathVariable String email) {
        List<Message> messages = messageService.getAllUnreadByEmail(email);

        if (messages != null) {
            List<MessageDTO> messageDTOs = new ArrayList<>();
            for (Message message : messages) {
                messageDTOs.add(convertToDTO(message));
            }
            return messageDTOs;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/getAllSent/{email}")
    public List<MessageDTO> getAllSent(@PathVariable String email) {
        List<Message> messages = messageService.getAllSentByEmail(email);

        if (messages != null) {
            List<MessageDTO> messageDTOs = new ArrayList<>();
            for (Message message : messages) {
                messageDTOs.add(convertToDTO(message));
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

    // Statisk metod för att konvertera Message till MessageDTO
    private static MessageDTO convertToDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getTitle(),
                message.getText(),
                message.getDateTime(),
                message.getIsRead(),
                message.getSender(),
                message.getReceiver()
        );
    }
}
