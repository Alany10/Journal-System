package journalSystem.controller;

import jakarta.transaction.Transactional;
import journalSystem.model.*;
import journalSystem.service.interfaces.IEncounterService;
import journalSystem.service.interfaces.IObservationService;
import journalSystem.service.interfaces.IUserService;
import journalSystem.service.interfaces.IDiagnosService;
import journalSystem.viewModel.ObservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/observation")
@CrossOrigin(origins = {"https://frontend-service:5173", "https://localhost:8000", "https://localhost:30000"}, allowedHeaders = "*")
public class ObservationController {
    private final IObservationService observationService;
    private final IUserService userService;
    private final IEncounterService encounterService;
    private final IDiagnosService diagnosService;
    private final UserController userController;

    @Autowired
    public ObservationController(IObservationService observationService, IUserService userService,
                                 IEncounterService encounterService, IDiagnosService diagnosService, UserController userController) {
        this.observationService = observationService;
        this.userService = userService;
        this.encounterService = encounterService;
        this.diagnosService = diagnosService;
        this.userController = userController;
    }

    @Transactional
    @GetMapping("/getAll")
    public ResponseEntity<List<ObservationDTO>> getAllObservations(@RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        List<Observation> observations = observationService.getAllObservations();
        if (observations != null) {
            List<ObservationDTO> observationDTOs = new ArrayList<>();
            for (Observation observation: observations){
                observationDTOs.add(Mapper.convertToDTO(observation));
            }
            return ResponseEntity.ok(observationDTOs);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @Transactional
    @GetMapping("/get/{id}")
    public ResponseEntity<ObservationDTO> getObservationById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        Observation observation = observationService.getObservationById(id);
        if (observation != null) {
            return ResponseEntity.ok(Mapper.convertToDTO(observation));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<String> createObservation(@RequestBody ObservationDTO observationDTO, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (observationDTO.getDescription() == null ||
                observationDTO.getPatientId() < 0 ||
                observationDTO.getPractitionerId() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User patient = userService.getPatientById(observationDTO.getPatientId());
        User practitioner = userService.getPractitionerById(observationDTO.getPractitionerId());
        Encounter encounter = encounterService.getEncounterById(observationDTO.getEncounterId());
        Diagnos diagnos = diagnosService.getDiagnosById(observationDTO.getDiagnosId());

        if (patient == null || practitioner == null || encounter == null || diagnos == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Observation observation = new Observation(observationDTO.getDescription(), patient, practitioner, encounter, diagnos);
        Observation createdObservation = observationService.createObservation(observation);
        return ResponseEntity.status(HttpStatus.CREATED).body("Observation created");
    }

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateObservation(@PathVariable int id, @RequestBody ObservationDTO observationDTO, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (observationDTO.getDescription() == null ||
                observationDTO.getPatientId() < 0 ||
                observationDTO.getPractitionerId() < 0 ||
                observationDTO.getEncounterId() < 0 ||
                observationDTO.getDiagnosId() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User patient = userService.getPatientById(observationDTO.getPatientId());
        User practitioner = userService.getPractitionerById(observationDTO.getPractitionerId());
        Encounter encounter = encounterService.getEncounterById(observationDTO.getEncounterId());
        Diagnos diagnos = diagnosService.getDiagnosById(observationDTO.getDiagnosId());

        if (patient == null || practitioner == null || encounter == null || diagnos == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Observation updatedObservation = observationService.updateObservation(id, new Observation(
                id,
                observationDTO.getDescription(),
                patient,
                practitioner,
                encounter,
                diagnos
        ));
        if (updatedObservation != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Observation updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @GetMapping("/getAllByDiagnos/{diagnosId}")
    public ResponseEntity<List<ObservationDTO>> getAllObservationsByDiagnos(@PathVariable int diagnosId, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (!diagnosService.existsById(diagnosId)) throw new IllegalArgumentException("No Diagnos With Id: " + diagnosId);

        List<Observation> observations = observationService.getAllObservationsByDiagnos(diagnosId);
        if (observations != null) {
            List<ObservationDTO> observationDTOs = new ArrayList<>();
            for (Observation observation : observations){
                observationDTOs.add(Mapper.convertToDTO(observation));
            }
            return ResponseEntity.ok(observationDTOs);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteObservation(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (id >= 0 && observationService.existsById(id)) {
            observationService.deleteObservation(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}