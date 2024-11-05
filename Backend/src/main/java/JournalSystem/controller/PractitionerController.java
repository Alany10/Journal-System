package JournalSystem.controller;

import JournalSystem.model.Practitioner;
import JournalSystem.service.interfaces.IPractitionerService;
import JournalSystem.viewModel.PractitionerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/practitioner")
public class PractitionerController {
    private final IPractitionerService PractitionerService;

    @Autowired
    public PractitionerController(IPractitionerService PractitionerService) {
        this.PractitionerService = PractitionerService;
    }

    private PractitionerDTO convertToDTO(Practitioner practitioner) {
        return new PractitionerDTO(
                practitioner.getId(),
                practitioner.getFirstName(),
                practitioner.getLastName(),
                practitioner.getPhoneNr(),
                practitioner.getRole()
        );
    }

    private List<PractitionerDTO> convertToDTOList(List<Practitioner> practitioners) {
        return practitioners.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<PractitionerDTO> getAllPractitioners() {
        List<Practitioner> practitioners = PractitionerService.getAllPractitioner();
        return convertToDTOList(practitioners);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PractitionerDTO> getPractitionerById(@PathVariable int id) {
        Practitioner practitioner = PractitionerService.getPractitionerById(id);
        if (practitioner != null) {
            return ResponseEntity.ok(convertToDTO(practitioner));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<PractitionerDTO> createPractitioner(@RequestBody PractitionerDTO practitionerDTO) {
        Practitioner practitioner = new Practitioner();
        practitioner.setFirstName(practitionerDTO.getFirstName());
        practitioner.setLastName(practitionerDTO.getLastName());
        practitioner.setPhoneNr(practitionerDTO.getPhoneNr());

        Practitioner createdPractitioner = PractitionerService.createPractitioner(practitioner);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdPractitioner));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PractitionerDTO> updatePractitioner(@PathVariable int id, @RequestBody PractitionerDTO practitionerDTO) {
        Practitioner updatedPractitioner = PractitionerService.updatePractitioner(id, new Practitioner(
                id,
                practitionerDTO.getFirstName(),
                practitionerDTO.getLastName(),
                practitionerDTO.getPhoneNr(),
                practitionerDTO.getRole()
        ));
        if (updatedPractitioner != null) {
            return ResponseEntity.ok(convertToDTO(updatedPractitioner));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePractitioner(@PathVariable int id) {
        PractitionerService.deletePractitioner(id);
        return ResponseEntity.noContent().build();
    }
}
