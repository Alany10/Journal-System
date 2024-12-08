package journalSystem.controller;

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
@CrossOrigin(origins = {"http://frontend-service:5173", "http://localhost:5173", "http://localhost:30000"}, allowedHeaders = "*")
public class DiagnosController {
    private final IDiagnosService diagnosService;
    private final IUserService userService;

    @Autowired
    public DiagnosController(IDiagnosService diagnosService, IUserService userService) {
        this.diagnosService = diagnosService;
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public List<DiagnosDTO> getAllDiagnoses() {
        List<Diagnos> diagnoses = diagnosService.getAllDiagnoses();
        if (diagnoses != null) {
            List<DiagnosDTO> diagnosDTOS = new ArrayList<>();
            for (Diagnos diagnos : diagnoses){
                diagnosDTOS.add(Mapper.convertToDTO(diagnos));
            }
            return diagnosDTOS;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DiagnosDTO> getDiagnosById(@PathVariable int id) {
        Diagnos diagnos = diagnosService.getDiagnosById(id);
        if (diagnos != null) {
            return ResponseEntity.ok(Mapper.convertToDTO(diagnos));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<DiagnosDTO> createDiagnos(@RequestBody DiagnosDTO diagnosDTO) {
        if (diagnosDTO.getName() == null || diagnosDTO.getPatientId() < 0 || diagnosDTO.getPractitionerId() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User patient = userService.getPatientById(diagnosDTO.getPatientId());
        User practitioner = userService.getPractitionerById(diagnosDTO.getPractitionerId());

        if (patient == null || practitioner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Diagnos diagnos = new Diagnos(diagnosDTO.getName(), patient , practitioner);
        Diagnos createdDiagnos = diagnosService.createDiagnos(diagnos);
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(createdDiagnos));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DiagnosDTO> updateDiagnos(@PathVariable int id, @RequestBody DiagnosDTO diagnosDTO) {
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
            return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(updatedDiagnos));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/establish/{id}")
    public ResponseEntity<DiagnosDTO> updateDiagnos(@PathVariable int id, @RequestBody String diagnosStatus) {
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
            return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(updatedDiagnos));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/getAllByPatient/{patientId}")
    public List<DiagnosDTO> getAllDiagnosesByPatient(@PathVariable int patientId) {
        if (userService.getPatientById(patientId) == null) throw new IllegalArgumentException("No Patient With Id: " + patientId);

        List<Diagnos> diagnoses = diagnosService.getAllDiagnosesByPatient(patientId);
        if (diagnoses != null) {
            List<DiagnosDTO> diagnosDTOS = new ArrayList<>();
            for (Diagnos diagnos : diagnoses){
                diagnosDTOS.add(Mapper.convertToDTO(diagnos));
            }
            return diagnosDTOS;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/getAllByPractitioner/{practitionerId}")
    public List<DiagnosDTO> getAllDiagnosesByPractitioner(@PathVariable int practitionerId) {
        if (userService.getPractitionerById(practitionerId) == null) throw new IllegalArgumentException("No Practitioner With Id: " + practitionerId);

        List<Diagnos> diagnoses = diagnosService.getAllDiagnosesByPractitioner(practitionerId);
        if (diagnoses != null) {
            List<DiagnosDTO> diagnosDTOS = new ArrayList<>();
            for (Diagnos diagnos : diagnoses){
                diagnosDTOS.add(Mapper.convertToDTO(diagnos));
            }
            return diagnosDTOS;
        } else {
            return new ArrayList<>();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDiagnos(@PathVariable int id) {
        if (id >= 0 && diagnosService.existsById(id)) {
            diagnosService.deleteDiagnos(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
