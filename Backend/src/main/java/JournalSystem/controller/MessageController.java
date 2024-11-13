package JournalSystem.controller;

import JournalSystem.model.*;
import JournalSystem.service.interfaces.IEncounterService;
import JournalSystem.service.interfaces.IMessageService;
import JournalSystem.service.interfaces.IPatientService;
import JournalSystem.service.interfaces.IPractitionerService;
import JournalSystem.viewModel.EncounterDTO;
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
    private final IPatientService patientService;
    private final IPractitionerService practitionerService;

    @Autowired
    public MessageController(IMessageService messageService, IPatientService patientService, IPractitionerService practitionerService) {
        this.messageService = messageService;
        this.patientService = patientService;
        this.practitionerService = practitionerService;
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

        Patient patient = patientService.getPatientById(messageDTO.getPatientId());
        Practitioner practitioner = practitionerService.getPractitionerById(messageDTO.getPractitionerId());

        if (patient == null || practitioner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Message message = new Message(messageDTO.getTitle(), messageDTO.getText(), Sender.valueOf(messageDTO.getSender().toUpperCase()), patient, practitioner);
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

        Patient patient = patientService.getPatientById(messageDTO.getPatientId());
        Practitioner practitioner = practitionerService.getPractitionerById(messageDTO.getPractitionerId());

        if (patient == null || practitioner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Message updatedMessage = messageService.updateMessage(id, new Message(
                id,
                messageDTO.getTitle(),
                messageDTO.getText(),
                messageDTO.getDateTime(),
                messageDTO.getIsRead(),
                Sender.valueOf(messageDTO.getSender().toUpperCase()),
                patient,
                practitioner
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

        Patient patient = patientService.getPatientById(message.getPatient().getId());
        Practitioner practitioner = practitionerService.getPractitionerById(message.getPractitioner().getId());

        if (patient == null || practitioner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Message updatedMessage = messageService.updateMessage(id, new Message(
                id,
                message.getTitle(),
                message.getText(),
                message.getDateTime(),
                true,
                Sender.valueOf(message.getSender().toString()),
                patient,
                practitioner
        ));

        if (updatedMessage != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(updatedMessage));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getAllSentByPatient/{patientId}")
    public List<MessageDTO> getAllSentByPatient(@PathVariable int patientId) {
        if (!patientService.existsById(patientId)) throw new IllegalArgumentException("No Patient With Id: " + patientId);

        List<Message> messages = messageService.getAllSentByPatientId(patientId);
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

    @GetMapping("/getAllSentByPractitioner/{practitionerId}")
    public List<MessageDTO> getAllSentByPractitioner(@PathVariable int practitionerId) {
        if (!practitionerService.existsById(practitionerId)) throw new IllegalArgumentException("No Practitioner With Id: " + practitionerId);

        List<Message> messages = messageService.getAllSentByPractitionerId(practitionerId);
        if (messages != null) {
            List<MessageDTO> messageDTOS = new ArrayList<>();
            for (Message message : messages){
                messageDTOS.add(Mapper.convertToDTO(message));
            }
            return messageDTOS;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/getAllReceivedByPatient/{patientId}")
    public List<MessageDTO> getAllreceivedByPatient(@PathVariable int patientId) {
        if (!patientService.existsById(patientId)) throw new IllegalArgumentException("No Patient With Id: " + patientId);

        List<Message> messages = messageService.getAllReceivedByPatientId(patientId);
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

    @GetMapping("/getAllReceivedByPractitioner/{practitionerId}")
    public List<MessageDTO> getAllReceivedByPractitioner(@PathVariable int practitionerId) {
        if (!practitionerService.existsById(practitionerId)) throw new IllegalArgumentException("No Practitioner With Id: " + practitionerId);

        List<Message> messages = messageService.getAllReceivedByPractitionerId(practitionerId);
        if (messages != null) {
            List<MessageDTO> messageDTOS = new ArrayList<>();
            for (Message message : messages){
                messageDTOS.add(Mapper.convertToDTO(message));
            }
            return messageDTOS;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/getAllUnreadByPatient/{patientId}")
    public List<MessageDTO> getAllUnreadByPatient(@PathVariable int patientId) {
        if (!patientService.existsById(patientId)) throw new IllegalArgumentException("No Patient With Id: " + patientId);

        List<Message> messages = messageService.getAllUnreadReceivedByPatientId(patientId);
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

    @GetMapping("/getAllUnreadByPractitioner/{practitionerId}")
    public List<MessageDTO> getAllUnreadByPractitioner(@PathVariable int practitionerId) {
        if (!practitionerService.existsById(practitionerId)) throw new IllegalArgumentException("No Practitioner With Id: " + practitionerId);

        List<Message> messages = messageService.getAllUnreadReceivedByPractitionerId(practitionerId);
        if (messages != null) {
            List<MessageDTO> messageDTOS = new ArrayList<>();
            for (Message message : messages){
                messageDTOS.add(Mapper.convertToDTO(message));
            }
            return messageDTOS;
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