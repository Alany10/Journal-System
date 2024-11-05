package FullstackLab1.JournalSystem.controller;

import FullstackLab1.JournalSystem.model.Patient;
import FullstackLab1.JournalSystem.model.Staff;
import FullstackLab1.JournalSystem.service.interfaces.IStaffService;
import FullstackLab1.JournalSystem.viewModel.StaffDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/staff")
public class StaffController {
    private final IStaffService staffService;

    @Autowired
    public StaffController(IStaffService staffService) {
        this.staffService = staffService;
    }

    // Konvertera Patient till PatientDTO
    private StaffDTO convertToDTO(Staff staff) {
        return new StaffDTO(
                staff.getId(),
                staff.getFirstName(),
                staff.getLastName(),
                staff.getPhoneNr()
        );
    }

    // Konvertera lista av Patients till lista av PatientDTOs
    private List<StaffDTO> convertToDTOList(List<Staff> staffs) {
        return staffs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<StaffDTO> getAllStaffs() {
        List<Staff> staffs = staffService.getAllStaffs();
        return convertToDTOList(staffs); // Returnera lista av PatientDTOs
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffDTO> getStaffById(@PathVariable int id) {
        Staff staff = staffService.getStaffById(id);
        if (staff != null) {
            return ResponseEntity.ok(convertToDTO(staff)); // Returnera PatientDTO om hittad
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returnera 404 om inte hittad
        }
    }

    @PostMapping
    public ResponseEntity<StaffDTO> createStaff(@RequestBody StaffDTO staffDTO) {
        // Konvertera PatientDTO till Patient
        Staff staff = new Staff();
        staff.setFirstName(staffDTO.getFirstName());
        staff.setLastName(staffDTO.getLastName());
        staff.setPhoneNr(staffDTO.getPhoneNr());

        Staff createdStaff = staffService.createStaff(staff);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdStaff)); // Returnera skapad PatientDTO
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffDTO> updateStaff(@PathVariable int id, @RequestBody StaffDTO staffDTO) {
        Staff updatedStaff = staffService.updateStaff(id, new Staff(
                id,
                staffDTO.getFirstName(),
                staffDTO.getLastName(),
                staffDTO.getPhoneNr()
        ));
        if (updatedStaff != null) {
            return ResponseEntity.ok(convertToDTO(updatedStaff)); // Returnera uppdaterad PatientDTO om lyckad
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returnera 404 om patient inte finns
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable int id) {
        staffService.deleteStaff(id); // Radera patient
        return ResponseEntity.noContent().build(); // Returnera 204 No Content
    }
}
