package JournalSystem.controller;

import JournalSystem.model.*;
import JournalSystem.service.interfaces.IDiagnosService;
import JournalSystem.service.interfaces.IPatientService;
import JournalSystem.service.interfaces.IPractitionerService;
import JournalSystem.viewModel.DiagnosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/diagnos")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class DiagnosController {
    private final IDiagnosService diagnosService;
    private final IPatientService patientService;
    private final IPractitionerService practitionerService;

    @Autowired
    public DiagnosController(IDiagnosService diagnosService, IPatientService patientService,
                             IPractitionerService practitionerService) {
        this.diagnosService = diagnosService;
        this.patientService = patientService;
        this.practitionerService = practitionerService;
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

        Patient patient = patientService.getPatientById(diagnosDTO.getPatientId());
        Practitioner practitioner = practitionerService.getPractitionerById(diagnosDTO.getPractitionerId());

        if (patient == null || practitioner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Diagnos diagnos = new Diagnos(diagnosDTO.getName(), patient ,practitioner);
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

        Patient patient = patientService.getPatientById(diagnosDTO.getPatientId());
        Practitioner practitioner = practitionerService.getPractitionerById(diagnosDTO.getPractitionerId());
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

    @PutMapping("/establish/{id}") // TODO
    public ResponseEntity<DiagnosDTO> updateDiagnos(@PathVariable int id, @RequestBody String diagnosStatus) {
        System.out.println(id + " "+ diagnosStatus);
        if (diagnosStatus == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        Diagnos diagnos = diagnosService.getDiagnosById(id);

        Patient patient = patientService.getPatientById(diagnos.getPatient().getId());
        Practitioner practitioner = practitionerService.getPractitionerById(diagnos.getPractitioner().getId());

        if (patient == null || practitioner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Diagnos updatedDiagnos = diagnosService.updateDiagnos(id, new Diagnos(
                id,
                diagnos.getName(),
                DiagnosStatus.valueOf(diagnosStatus.toUpperCase()),
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
        if (!patientService.existsById(patientId)) throw new IllegalArgumentException("No Patient With Id: " + patientId);

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
        if (!patientService.existsById(practitionerId)) throw new IllegalArgumentException("No Pactitioner With Id: " + practitionerId);

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
