package journalSystem.controller;

import jakarta.transaction.Transactional;
import journalSystem.model.*;
import journalSystem.service.interfaces.IEncounterService;
import journalSystem.service.interfaces.IUserService;
import journalSystem.viewModel.EncounterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/encounter")
@CrossOrigin(origins = {"https://frontend-service:5173", "https://localhost:8000", "https://localhost:30000"}, allowedHeaders = "*")
public class EncounterController {
    private final IEncounterService encounterService;
    private final IUserService userService;
    private final UserController userController;

    @Autowired
    public EncounterController(IEncounterService encounterService, IUserService userService, UserController userController) {
        this.encounterService = encounterService;
        this.userService = userService;
        this.userController = userController;
    }

    @Transactional
    @GetMapping("/getAll")
    public ResponseEntity<List<EncounterDTO>> getAllEncounters(@RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        List<Encounter> encounters = encounterService.getAllEncounters();
        if (encounters != null) {
            List<EncounterDTO> encounterDTOs = new ArrayList<>();
            for (Encounter encounter: encounters){
                encounterDTOs.add(Mapper.convertToDTO(encounter));
            }
            return ResponseEntity.ok(encounterDTOs);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @Transactional
    @GetMapping("/get/{id}")
    public ResponseEntity<EncounterDTO> getEncounterById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        Encounter encounter = encounterService.getEncounterById(id);
        if (encounter != null) {
            return ResponseEntity.ok(Mapper.convertToDTO(encounter));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<String> createEncounter(@RequestBody EncounterDTO encounterDTO, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (encounterDTO.getDateTime() == null ||
                encounterDTO.getPatientId() < 0 ||
                encounterDTO.getPractitionerId() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User patient = userService.getPatientById(encounterDTO.getPatientId());
        User user = userService.getPractitionerById(encounterDTO.getPractitionerId());

        if (patient == null || user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Encounter encounter = new Encounter(encounterDTO.getDateTime(), patient, user);
        Encounter createdEncounter = encounterService.createEncounter(encounter);
        return ResponseEntity.status(HttpStatus.CREATED).body("Encounter created");
    }

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEncounter(@PathVariable int id, @RequestBody EncounterDTO encounterDTO, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (encounterDTO.getDateTime() == null ||
                encounterDTO.getPatientId() < 0 ||
                encounterDTO.getPractitionerId() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User patient = userService.getPatientById(encounterDTO.getPatientId());
        User user = userService.getPractitionerById(encounterDTO.getPractitionerId());

        if (patient == null || user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Encounter updatedEncounter = encounterService.updateEncounter(id, new Encounter(
                id,
                encounterDTO.getDateTime(),
                patient,
                user
        ));
        if (updatedEncounter != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Encounter updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @GetMapping("/getAllByPatient/{patientId}")
    public ResponseEntity<List<EncounterDTO>> getAllEncoutersByPatient(@PathVariable int patientId, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (userService.getPatientById(patientId) == null) throw new IllegalArgumentException("No Patient With Id: " + patientId);

        List<Encounter> encouters = encounterService.getAllEncountersByPatientId(patientId);

        if (encouters != null) {
            List<EncounterDTO> encouterDTOS = new ArrayList<>();
            for (Encounter encounter : encouters){
                encouterDTOS.add(Mapper.convertToDTO(encounter));
            }
            return ResponseEntity.ok(encouterDTOS);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @Transactional
    @GetMapping("/getAllByPractitioner/{practitionerId}")
    public ResponseEntity<List<EncounterDTO>> getAllEncoutersByPractitioner(@PathVariable int practitionerId, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (userService.getPractitionerById(practitionerId) == null) throw new IllegalArgumentException("No Practitioner With Id: " + practitionerId);

        List<Encounter> encouters = encounterService.getAllEncountersByPractitionerId(practitionerId);

        if (encouters != null) {
            List<EncounterDTO> encouterDTOS = new ArrayList<>();
            for (Encounter encounter : encouters){
                encouterDTOS.add(Mapper.convertToDTO(encounter));
            }
            return ResponseEntity.ok(encouterDTOS);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEncounter(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (id >= 0 && encounterService.existsById(id)) {
            encounterService.deleteEncounter(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}