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

    private DoctorDTO convertToDTO(Doctor Doctor) {
        return new DoctorDTO(
                Doctor.getId(),
                Doctor.getFirstName(),
                Doctor.getLastName(),
                Doctor.getPhoneNr()
        );
    }

    private List<DoctorDTO> convertToDTOList(List<Doctor> doctors) {
        return doctors.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return convertToDTOList(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable int id) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor != null) {
            return ResponseEntity.ok(convertToDTO(doctor));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(doctorDTO.getFirstName());
        doctor.setLastName(doctorDTO.getLastName());
        doctor.setPhoneNr(doctorDTO.getPhoneNr());

        Doctor createdDoctor = doctorService.createDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdDoctor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable int id, @RequestBody DoctorDTO doctorDTO) {
        Doctor updatedDoctor = doctorService.updateDoctor(id, new Doctor(
                id,
                doctorDTO.getFirstName(),
                doctorDTO.getLastName(),
                doctorDTO.getPhoneNr()
        ));
        if (updatedDoctor != null) {
            return ResponseEntity.ok(convertToDTO(updatedDoctor));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable int id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
