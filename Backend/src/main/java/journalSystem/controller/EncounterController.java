package journalSystem.controller;

import journalSystem.model.*;
import journalSystem.service.interfaces.IEncounterService;
import journalSystem.service.interfaces.IUserService;
import journalSystem.viewModel.EncounterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/encounter")
@CrossOrigin(origins = {"http://frontend-service:5173", "http://localhost:5173", "http://localhost:30000"}, allowedHeaders = "*")
public class EncounterController {
    private final IEncounterService encounterService;
    private final IUserService userService;

    @Autowired
    public EncounterController(IEncounterService encounterService, IUserService userService) {
        this.encounterService = encounterService;
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public List<EncounterDTO> getAllEncounters() {
        List<Encounter> encounters = encounterService.getAllEncounters();
        if (encounters != null) {
            List<EncounterDTO> encounterDTOs = new ArrayList<>();
            for (Encounter encounter: encounters){
                encounterDTOs.add(Mapper.convertToDTO(encounter));
            }
            return encounterDTOs;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EncounterDTO> getEncounterById(@PathVariable int id) {
        Encounter encounter = encounterService.getEncounterById(id);
        if (encounter != null) {
            return ResponseEntity.ok(Mapper.convertToDTO(encounter));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<EncounterDTO> createEncounter(@RequestBody EncounterDTO encounterDTO) {
        System.out.println(encounterDTO.getDateTime() + " " + encounterDTO.getPatientId() + encounterDTO.getPractitionerId());
        if (encounterDTO.getDateTime() == null ||
                encounterDTO.getPatientId() < 0 ||
                encounterDTO.getPractitionerId() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User patient = userService.getPatientById(encounterDTO.getPatientId());
        User user = userService.getPractitionerById(encounterDTO.getPractitionerId());

        if (patient == null || user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Encounter encounter = new Encounter(encounterDTO.getDateTime(), patient, user);
        Encounter createdEncounter = encounterService.createEncounter(encounter);
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(createdEncounter));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EncounterDTO> updateEncounter(@PathVariable int id, @RequestBody EncounterDTO encounterDTO) {
        if (encounterDTO.getDateTime() == null ||
                encounterDTO.getPatientId() < 0 ||
                encounterDTO.getPractitionerId() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User patient = userService.getPatientById(encounterDTO.getPatientId());
        User user = userService.getPractitionerById(encounterDTO.getPractitionerId());

        if (patient == null || user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Encounter updatedEncounter = encounterService.updateEncounter(id, new Encounter(
                id,
                encounterDTO.getDateTime(),
                patient,
                user
        ));
        if (updatedEncounter != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(updatedEncounter));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getAllByPatient/{patientId}")
    public List<EncounterDTO> getAllEncoutersByPatient(@PathVariable int patientId) {
        if (userService.getPatientById(patientId) == null) throw new IllegalArgumentException("No Patient With Id: " + patientId);

        List<Encounter> encouters = encounterService.getAllEncountersByPatientId(patientId);

        if (encouters != null) {
            List<EncounterDTO> encouterDTOS = new ArrayList<>();
            for (Encounter encounter : encouters){
                encouterDTOS.add(Mapper.convertToDTO(encounter));
            }
            return encouterDTOS;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/getAllByPractitioner/{practitionerId}")
    public List<EncounterDTO> getAllEncoutersByPractitioner(@PathVariable int practitionerId) {
        if (userService.getPractitionerById(practitionerId) == null) throw new IllegalArgumentException("No Practitioner With Id: " + practitionerId);

        List<Encounter> encouters = encounterService.getAllEncountersByPractitionerId(practitionerId);

        if (encouters != null) {
            List<EncounterDTO> encouterDTOS = new ArrayList<>();
            for (Encounter encounter : encouters){
                encouterDTOS.add(Mapper.convertToDTO(encounter));
            }
            return encouterDTOS;
        } else {
            return new ArrayList<>();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEncounter(@PathVariable int id) {
        if (id >= 0 && encounterService.existsById(id)) {
            encounterService.deleteEncounter(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}