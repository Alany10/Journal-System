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

    private final IPractitionerService practitionerService;

    @Autowired
    public PractitionerController(IPractitionerService PractitionerService) {
        this.practitionerService = PractitionerService;
    }

<<<<<<< Updated upstream
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
=======
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
>>>>>>> Stashed changes
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PractitionerDTO> getPractitionerById(@PathVariable int id) {
        Practitioner practitioner = practitionerService.getPractitionerById(id);
        if (practitioner != null) {
            return ResponseEntity.ok(convertToDTO(practitioner));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<PractitionerDTO> createPractitioner(@RequestBody PractitionerDTO practitionerDTO) {
<<<<<<< Updated upstream
        Practitioner practitioner = new Practitioner();
        practitioner.setFirstName(practitionerDTO.getFirstName());
        practitioner.setLastName(practitionerDTO.getLastName());
        practitioner.setPhoneNr(practitionerDTO.getPhoneNr());

        Practitioner createdPractitioner = PractitionerService.createPractitioner(practitioner);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdPractitioner));
=======
        if (practitionerDTO.getFirstName() == null ||
                practitionerDTO.getLastName() == null ||
                practitionerDTO.getPhoneNr() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        else {
            Practitioner practitioner = new Practitioner(practitionerDTO.getFirstName(),
                    practitionerDTO.getLastName(),
                    practitionerDTO.getPhoneNr(),
                    Role.valueOf(practitionerDTO.getRole())
                    );

            Practitioner createdPractitioner = practitionerService.createPractitioner(practitioner);
            return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(createdPractitioner));
        }
>>>>>>> Stashed changes
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PractitionerDTO> updatePractitioner(@PathVariable int id, @RequestBody PractitionerDTO practitionerDTO) {
        if (practitionerDTO.getFirstName() == null ||
                practitionerDTO.getLastName() == null ||
                practitionerDTO.getPhoneNr() == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        //TODO
        Practitioner updatedPractitioner = practitionerService.updatePractitioner(id, new Practitioner(
                id,
                practitionerDTO.getFirstName(),
                practitionerDTO.getLastName(),
                practitionerDTO.getPhoneNr(),
<<<<<<< Updated upstream
                practitionerDTO.getRole()
        ));
        if (updatedPractitioner != null) {
            return ResponseEntity.ok(convertToDTO(updatedPractitioner));
=======
                Role.valueOf(practitionerDTO.getRole())
        ));
        if (updatedPractitioner != null) {
            return ResponseEntity.ok().build();
>>>>>>> Stashed changes
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
}
