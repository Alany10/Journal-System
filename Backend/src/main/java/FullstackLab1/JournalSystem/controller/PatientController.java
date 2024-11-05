package FullstackLab1.JournalSystem.controller;

import FullstackLab1.JournalSystem.viewModel.PatientDTO;
import FullstackLab1.JournalSystem.model.Patient;
import FullstackLab1.JournalSystem.service.interfaces.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patient") // Grundläggande URL för API
public class PatientController {

    private final IPatientService patientService;

    @Autowired
    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    // Konvertera Patient till PatientDTO
    private PatientDTO convertToDTO(Patient patient) {
        return new PatientDTO(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhoneNr()
        );
    }

    // Konvertera lista av Patients till lista av PatientDTOs
    private List<PatientDTO> convertToDTOList(List<Patient> patients) {
        return patients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return convertToDTOList(patients); // Returnera lista av PatientDTOs
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable int id) {
        Patient patient = patientService.getPatientById(id);
        if (patient != null) {
            return ResponseEntity.ok(convertToDTO(patient)); // Returnera PatientDTO om hittad
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returnera 404 om inte hittad
        }
    }

    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO) {
        // Konvertera PatientDTO till Patient
        Patient patient = new Patient();
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setPhoneNr(patientDTO.getPhoneNr());

        Patient createdPatient = patientService.createPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdPatient)); // Returnera skapad PatientDTO
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable int id, @RequestBody PatientDTO patientDTO) {
        Patient updatedPatient = patientService.updateStaff(id, new Patient(
                id,
                patientDTO.getFirstName(),
                patientDTO.getLastName(),
                patientDTO.getPhoneNr()
        ));
        if (updatedPatient != null) {
            return ResponseEntity.ok(convertToDTO(updatedPatient)); // Returnera uppdaterad PatientDTO om lyckad
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returnera 404 om patient inte finns
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable int id) {
        patientService.deletePatient(id); // Radera patient
        return ResponseEntity.noContent().build(); // Returnera 204 No Content
    }
}
