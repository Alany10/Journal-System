package journalSystem.controller;

import jakarta.transaction.Transactional;
import journalSystem.model.*;
import journalSystem.service.interfaces.IDiagnosService;
import journalSystem.service.interfaces.IUserService;
import journalSystem.viewModel.DiagnosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/diagnos")
@CrossOrigin(origins = {"https://frontend-service:5173", "https://localhost:8000", "https://localhost:30000"}, allowedHeaders = "*")
public class DiagnosController {
    private final IDiagnosService diagnosService;
    private final IUserService userService;
    private final UserController userController;

    @Autowired
    public DiagnosController(IDiagnosService diagnosService, IUserService userService, UserController userController) {
        this.diagnosService = diagnosService;
        this.userService = userService;
        this.userController = userController;
    }

    @Transactional
    @GetMapping("/getAll")
    public ResponseEntity<List<DiagnosDTO>> getAllDiagnoses(@RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        List<Diagnos> diagnoses = diagnosService.getAllDiagnoses();
        if (diagnoses != null) {
            List<DiagnosDTO> diagnosDTOS = new ArrayList<>();
            for (Diagnos diagnos : diagnoses){
                diagnosDTOS.add(Mapper.convertToDTO(diagnos));
            }
            return ResponseEntity.ok(diagnosDTOS);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @Transactional
    @GetMapping("/get/{id}")
    public ResponseEntity<DiagnosDTO> getDiagnosById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        Diagnos diagnos = diagnosService.getDiagnosById(id);
        if (diagnos != null) {
            return ResponseEntity.ok(Mapper.convertToDTO(diagnos));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<String> createDiagnos(@RequestBody DiagnosDTO diagnosDTO, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (diagnosDTO.getName() == null || diagnosDTO.getPatientId() < 0 || diagnosDTO.getPractitionerId() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User patient = userService.getPatientById(diagnosDTO.getPatientId());
        User practitioner = userService.getPractitionerById(diagnosDTO.getPractitionerId());

        if (patient == null || practitioner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Diagnos diagnos = new Diagnos(diagnosDTO.getName(), patient , practitioner);
        diagnosService.createDiagnos(diagnos);
        return ResponseEntity.status(HttpStatus.CREATED).body("Diagnos created");
    }

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDiagnos(@PathVariable int id, @RequestBody DiagnosDTO diagnosDTO, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (diagnosDTO.getName() == null ||
                diagnosDTO.getPatientId() < 0 ||
                diagnosDTO.getPractitionerId() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User patient = userService.getPatientById(diagnosDTO.getPatientId());
        User practitioner = userService.getPractitionerById(diagnosDTO.getPractitionerId());
        if (patient == null || practitioner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Validera DiagnosStatus
        DiagnosStatus status;
        try {
            status = DiagnosStatus.valueOf(diagnosDTO.getDiagnosStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Diagnos updatedDiagnos = diagnosService.updateDiagnos(id, new Diagnos(
                id,
                diagnosDTO.getName(),
                status,
                patient,
                practitioner
        ));

        if (updatedDiagnos != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Diagnos updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @PutMapping("/establish/{id}")
    public ResponseEntity<String> updateDiagnos(@PathVariable int id, @RequestBody String diagnosStatus, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (diagnosStatus == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        String cleanedStatus = diagnosStatus.replace("\"", "").trim().toUpperCase();

        Diagnos diagnos = diagnosService.getDiagnosById(id);

        User patient = userService.getPatientById(diagnos.getPatient().getId());
        User practitioner = userService.getPractitionerById(diagnos.getPractitioner().getId());

        if (patient == null || practitioner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Diagnos updatedDiagnos = diagnosService.updateDiagnos(id, new Diagnos(
                id,
                diagnos.getName(),
                DiagnosStatus.valueOf(cleanedStatus.toUpperCase()),
                patient,
                practitioner
        ));

        if (updatedDiagnos != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Diagnos established");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @GetMapping("/getAllByPatient/{patientId}")
    public ResponseEntity<List<DiagnosDTO>> getAllDiagnosesByPatient(@PathVariable int patientId, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (userService.getPatientById(patientId) == null) throw new IllegalArgumentException("No Patient With Id: " + patientId);

        List<Diagnos> diagnoses = diagnosService.getAllDiagnosesByPatient(patientId);
        if (diagnoses != null) {
            List<DiagnosDTO> diagnosDTOS = new ArrayList<>();
            for (Diagnos diagnos : diagnoses){
                diagnosDTOS.add(Mapper.convertToDTO(diagnos));
            }
            return ResponseEntity.ok(diagnosDTOS);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @Transactional
    @GetMapping("/getAllByPractitioner/{practitionerId}")
    public ResponseEntity<List<DiagnosDTO>> getAllDiagnosesByPractitioner(@PathVariable int practitionerId, @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (userService.getPractitionerById(practitionerId) == null) throw new IllegalArgumentException("No Practitioner With Id: " + practitionerId);

        List<Diagnos> diagnoses = diagnosService.getAllDiagnosesByPractitioner(practitionerId);
        if (diagnoses != null) {
            List<DiagnosDTO> diagnosDTOS = new ArrayList<>();
            for (Diagnos diagnos : diagnoses){
                diagnosDTOS.add(Mapper.convertToDTO(diagnos));
            }
            return ResponseEntity.ok(diagnosDTOS);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDiagnos(@PathVariable int id,  @RequestHeader("Authorization") String token) {
        if (!userController.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Returnera 401 om token är ogiltig
        }

        if (id >= 0 && diagnosService.existsById(id)) {
            diagnosService.deleteDiagnos(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
