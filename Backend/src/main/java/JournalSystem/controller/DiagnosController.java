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
        Diagnos updatedDiagnos = diagnosService.updateDiagnos(id, new Diagnos(
                id,
                diagnosDTO.getName(),
                patient,
                practitioner
        ));
        if (updatedDiagnos != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(updatedDiagnos));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
