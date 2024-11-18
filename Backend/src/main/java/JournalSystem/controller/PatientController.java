package JournalSystem.controller;

import JournalSystem.model.Role;
import JournalSystem.model.login.LoginRequest;
import JournalSystem.model.login.LoginResponse;
import JournalSystem.viewModel.PatientDTO;
import JournalSystem.model.Patient;
import JournalSystem.service.interfaces.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/patient")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class PatientController {

    private final IPatientService patientService;

    @Autowired
    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

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
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable int id) {
        Patient patient = patientService.getPatientById(id);
        if (patient != null) {
            return ResponseEntity.ok(Mapper.convertToDTO(patient));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO) {
        if (patientDTO.getEmail() == null ||
                patientDTO.getName() == null ||
                patientDTO.getPassword() == null ||
                patientDTO.getPhoneNr() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();


        Patient patient = new Patient(patientDTO.getEmail(),
                    patientDTO.getName(),
                    patientDTO.getPassword(),
                    patientDTO.getPhoneNr());

        Patient createdPatient = patientService.createPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(createdPatient));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable int id, @RequestBody PatientDTO patientDTO) {
        if (patientDTO.getEmail() == null ||
                patientDTO.getName() == null ||
                patientDTO.getPassword() == null ||
                patientDTO.getPhoneNr() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        Patient updatedPatient = patientService.updatePatient(id, new Patient(
                id,
                patientDTO.getEmail(),
                patientDTO.getName(),
                patientDTO.getPassword(),
                patientDTO.getPhoneNr()
        ));

        if (updatedPatient != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(updatedPatient));

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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        boolean loginSuccessful = patientService.verifyLogin(request.getEmail(), request.getPassword(), Role.valueOf(request.getRole().toUpperCase()));
        if (loginSuccessful) {
            int patientId = patientService.getIdByEmail(request.getEmail()); // Assuming you have a method to retrieve the ID
            return ResponseEntity.ok(new LoginResponse("Login successful", patientId));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid email or password"));
        }
    }
}
