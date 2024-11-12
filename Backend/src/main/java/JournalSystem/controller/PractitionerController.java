package JournalSystem.controller;

import JournalSystem.model.Practitioner;
import JournalSystem.model.Role;
import JournalSystem.model.login.LoginRequest;
import JournalSystem.model.login.LoginResponse;
import JournalSystem.service.interfaces.IPractitionerService;
import JournalSystem.viewModel.PractitionerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/practitioner")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class PractitionerController {

    private final IPractitionerService practitionerService;

    @Autowired
    public PractitionerController(IPractitionerService practitionerService) {
        this.practitionerService = practitionerService;
    }

    private PractitionerDTO convertToDTO(Practitioner practitioner) {
        return new PractitionerDTO(
                practitioner.getId(),
                practitioner.getEmail(),
                practitioner.getName(),
                practitioner.getPassword(),
                practitioner.getPhoneNr(),
                practitioner.getRole().toString()
        );
    }

    @GetMapping("/getAll")
    public List<PractitionerDTO> getAllPractitioners() {
        List<Practitioner> practitioners = practitionerService.getAllPractitioner();
        if (practitioners != null) {
            List<PractitionerDTO> practitionerDTOs = new ArrayList<>();
            for (Practitioner practitioner: practitioners){
                practitionerDTOs.add(Mapper.convertToDTO(practitioner));
            }
            return practitionerDTOs;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PractitionerDTO> getPractitionerById(@PathVariable int id) {
        Practitioner practitioner = practitionerService.getPractitionerById(id);
        if (practitioner != null) {
            return ResponseEntity.ok(Mapper.convertToDTO(practitioner));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<PractitionerDTO> createPractitioner(@RequestBody PractitionerDTO practitionerDTO) {
        if (practitionerDTO.getEmail() == null ||
                practitionerDTO.getName() == null ||
                practitionerDTO.getPassword() == null ||
                practitionerDTO.getPhoneNr() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();


        Practitioner practitioner = new Practitioner(practitionerDTO.getEmail(),
                    practitionerDTO.getName(),
                    practitionerDTO.getPassword(),
                    practitionerDTO.getPhoneNr(),
                    Role.valueOf(practitionerDTO.getRole().toUpperCase())
                    );

        Practitioner createdPractitioner = practitionerService.createPractitioner(practitioner);
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(createdPractitioner));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PractitionerDTO> updatePractitioner(@PathVariable int id, @RequestBody PractitionerDTO practitionerDTO) {
        if (practitionerDTO.getEmail() == null ||
                practitionerDTO.getName() == null ||
                practitionerDTO.getPassword() == null ||
                practitionerDTO.getPhoneNr() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        Practitioner updatedPractitioner = practitionerService.updatePractitioner(id, new Practitioner(
                id,
                practitionerDTO.getEmail(),
                practitionerDTO.getName(),
                practitionerDTO.getPassword(),
                practitionerDTO.getPhoneNr(),
                Role.valueOf(practitionerDTO.getRole().toUpperCase())
        ));

        if (updatedPractitioner != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePractitioner(@PathVariable int id) {
        if (id >= 0 && practitionerService.existsById(id)) {
            practitionerService.deletePractitioner(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        boolean loginSuccessful = practitionerService.verifyLogin(request.getEmail(), request.getPassword());
        if (loginSuccessful) {
            int practitionerId = practitionerService.getIdByEmail(request.getEmail()); // Assuming you have a method to retrieve the ID
            return ResponseEntity.ok(new LoginResponse("Login successful", practitionerId));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid email or password"));
        }
    }
}
