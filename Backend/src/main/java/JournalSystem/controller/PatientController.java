package JournalSystem.controller;

import JournalSystem.viewModel.PatientDTO;
import JournalSystem.model.Patient;
import JournalSystem.service.interfaces.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final IPatientService patientService;

    @Autowired
    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

<<<<<<< Updated upstream

    private PatientDTO convertToDTO(Patient patient) {
        return new PatientDTO(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhoneNr()
        );
    }

    private List<PatientDTO> convertToDTOList(List<Patient> patients) {
        return patients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return convertToDTOList(patients);
=======
    @GetMapping("/getAll")
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();

        if (patients != null) {
            List<PatientDTO> patientDTOs = new ArrayList<>();
            for (Patient patient: patients){
                patientDTOs.add(Mapper.convertToDTO(patient));
            }

            return patientDTOs;
        } else {
            return new ArrayList<>();
        }
>>>>>>> Stashed changes
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable int id) {
        Patient patient = patientService.getPatientById(id);
        if (patient != null) {
            return ResponseEntity.ok(convertToDTO(patient));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO) {
        if (patientDTO.getFirstName() == null ||
                patientDTO.getLastName() == null ||
                patientDTO.getPhoneNr() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

<<<<<<< Updated upstream
        Patient createdPatient = patientService.createPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdPatient));
=======
        else {
            Patient patient = new Patient(patientDTO.getFirstName(),
                    patientDTO.getLastName(),
                    patientDTO.getPhoneNr());

            Patient createdPatient = patientService.createPatient(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(createdPatient));
        }
>>>>>>> Stashed changes
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable int id, @RequestBody PatientDTO patientDTO) {
        if (patientDTO.getFirstName() == null ||
                patientDTO.getLastName() == null ||
                patientDTO.getPhoneNr() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        //TODO
        Patient updatedPatient = patientService.updatePatient(id, new Patient(
                id,
                patientDTO.getFirstName(),
                patientDTO.getLastName(),
                patientDTO.getPhoneNr()
        ));
        if (updatedPatient != null) {
<<<<<<< Updated upstream
            return ResponseEntity.ok(convertToDTO(updatedPatient));
=======
            return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(updatedPatient));
>>>>>>> Stashed changes
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable int id) {
        if (id >= 0 && patientService.existsById(id)) {
            patientService.deletePatient(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
