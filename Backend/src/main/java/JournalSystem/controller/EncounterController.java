package JournalSystem.controller;

import JournalSystem.model.*;
import JournalSystem.service.interfaces.IEncounterService;
import JournalSystem.service.interfaces.IPatientService;
import JournalSystem.service.interfaces.IPractitionerService;
import JournalSystem.viewModel.EncounterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/encounter")
public class EncounterController {
    private final IEncounterService encounterService;
    private final IPatientService patientService;
    private final IPractitionerService practitionerService;

    @Autowired
    public EncounterController(IEncounterService encounterService, IPatientService patientService, IPractitionerService practitionerService) {
        this.encounterService = encounterService;
        this.patientService = patientService;
        this.practitionerService = practitionerService;
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
        if (encounterDTO.getDateTime() == null ||
                encounterDTO.getPatientId() < 0 ||
                encounterDTO.getPractitionerId() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Patient patient = patientService.getPatientById(encounterDTO.getPatientId());
        Practitioner practitioner = practitionerService.getPractitionerById(encounterDTO.getPractitionerId());

        if (patient == null || practitioner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Encounter encounter = new Encounter(encounterDTO.getDateTime(), patient, practitioner);
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
        Patient patient = patientService.getPatientById(encounterDTO.getPatientId());
        Practitioner practitioner = practitionerService.getPractitionerById(encounterDTO.getPractitionerId());
        if (patient == null || practitioner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Encounter updatedEncounter = encounterService.updateEncounter(id, new Encounter(
                id,
                encounterDTO.getDateTime(),
                patient,
                practitioner
        ));
        if (updatedEncounter != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(updatedEncounter));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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