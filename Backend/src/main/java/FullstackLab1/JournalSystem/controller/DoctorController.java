package FullstackLab1.JournalSystem.controller;

import FullstackLab1.JournalSystem.model.Doctor;
import FullstackLab1.JournalSystem.service.interfaces.IDoctorService;
import FullstackLab1.JournalSystem.viewModel.DoctorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final IDoctorService doctorService;

    @Autowired
    public DoctorController(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // Konvertera Patient till PatientDTO
    private DoctorDTO convertToDTO(Doctor Doctor) {
        return new DoctorDTO(
                Doctor.getId(),
                Doctor.getFirstName(),
                Doctor.getLastName(),
                Doctor.getPhoneNr()
        );
    }

    // Konvertera lista av Patients till lista av PatientDTOs
    private List<DoctorDTO> convertToDTOList(List<Doctor> doctors) {
        return doctors.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return convertToDTOList(doctors); // Returnera lista av PatientDTOs
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable int id) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor != null) {
            return ResponseEntity.ok(convertToDTO(doctor)); // Returnera PatientDTO om hittad
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returnera 404 om inte hittad
        }
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        // Konvertera PatientDTO till Patient
        Doctor doctor = new Doctor();
        doctor.setFirstName(doctorDTO.getFirstName());
        doctor.setLastName(doctorDTO.getLastName());
        doctor.setPhoneNr(doctorDTO.getPhoneNr());

        Doctor createdStaff = doctorService.createDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdStaff)); // Returnera skapad PatientDTO
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateStaff(@PathVariable int id, @RequestBody DoctorDTO doctorDTO) {
        Doctor updatedDoctor = doctorService.updateDoctor(id, new Doctor(
                id,
                doctorDTO.getFirstName(),
                doctorDTO.getLastName(),
                doctorDTO.getPhoneNr()
        ));
        if (updatedDoctor != null) {
            return ResponseEntity.ok(convertToDTO(updatedDoctor)); // Returnera uppdaterad PatientDTO om lyckad
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returnera 404 om patient inte finns
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable int id) {
        doctorService.deleteDoctor(id); // Radera patient
        return ResponseEntity.noContent().build(); // Returnera 204 No Content
    }
}
