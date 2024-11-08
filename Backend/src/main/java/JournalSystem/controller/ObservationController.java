package JournalSystem.controller;

import JournalSystem.model.Encounter;
import JournalSystem.model.Observation;
import JournalSystem.model.Patient;
import JournalSystem.model.Practitioner;
import JournalSystem.service.interfaces.IEncounterService;
import JournalSystem.service.interfaces.IObservationService;
import JournalSystem.service.interfaces.IPatientService;
import JournalSystem.service.interfaces.IPractitionerService;
import JournalSystem.viewModel.ObservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/observation")
public class ObservationController {
    private final IObservationService observationService;
    private final IPatientService patientService;
    private final IPractitionerService practitionerService;
    private final IEncounterService encounterService;

    @Autowired
    public ObservationController(IObservationService observationService, IPatientService patientService,
                                 IPractitionerService practitionerService, IEncounterService encounterService) {
        this.observationService = observationService;
        this.patientService = patientService;
        this.practitionerService = practitionerService;
        this.encounterService = encounterService;
    }

    @GetMapping("/getAll")
    public List<ObservationDTO> getAllObservations() {
        List<Observation> observations = observationService.getAllObservations();
        if (observations != null) {
            List<ObservationDTO> observationDTOs = new ArrayList<>();
            for (Observation observation: observations){
                observationDTOs.add(Mapper.convertToDTO(observation));
            }
            return observationDTOs;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ObservationDTO> getObservationById(@PathVariable int id) {
        Observation observation = observationService.getObservationById(id);
        if (observation != null) {
            return ResponseEntity.ok(Mapper.convertToDTO(observation));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ObservationDTO> createObservation(@RequestBody ObservationDTO observationDTO) {
        if (observationDTO.getDescription() == null || observationDTO.getDateTime() == null ||
                observationDTO.getPatientId() < 0 || observationDTO.getPractitionerId() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Patient patient = patientService.getPatientById(observationDTO.getPatientId());
        Practitioner practitioner = practitionerService.getPractitionerById(observationDTO.getPractitionerId());
        Encounter encounter = encounterService.getEncounterById(observationDTO.getEncounterId());
        if (patient == null || practitioner == null || encounter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Observation observation = new Observation(observationDTO.getDescription(), observationDTO.getDateTime(), patient, practitioner,encounter);
        Observation createdObservation = observationService.createObservation(observation);
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(createdObservation));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ObservationDTO> updateObservation(@PathVariable int id, @RequestBody ObservationDTO observationDTO) {
        if (observationDTO.getDescription() == null ||
                observationDTO.getDateTime() == null ||
                observationDTO.getPatientId() < 0 ||
                observationDTO.getPractitionerId() < 0 ||
                observationDTO.getEncounterId() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Patient patient = patientService.getPatientById(observationDTO.getPatientId());
        Practitioner practitioner = practitionerService.getPractitionerById(observationDTO.getPractitionerId());
        Encounter encounter = encounterService.getEncounterById(observationDTO.getEncounterId());
        if (patient == null || practitioner == null || encounter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Observation updatedObservation = observationService.updateObservation(id, new Observation(
                id,
                observationDTO.getDescription(),
                observationDTO.getDateTime(),
                patient,
                practitioner,
                encounter
        ));
        if (updatedObservation != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.convertToDTO(updatedObservation));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteObservation(@PathVariable int id) {
        if (id >= 0 && observationService.existsById(id)) {
            observationService.deleteObservation(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}