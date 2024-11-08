package JournalSystem.controller;

import JournalSystem.model.Encounter;
import JournalSystem.model.Observation;
import JournalSystem.model.Patient;
import JournalSystem.model.Practitioner;
import JournalSystem.viewModel.*;
import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static PatientDTO convertToDTO(Patient patient){
        List<Encounter> encounters = patient.getEncounters();
        List<EncounterDTO> encounterDTOs = new ArrayList<>();
        List<Observation> observations = patient.getObservations();
        List<ObservationDTO> observationDTOs = new ArrayList<>();
        for (Encounter encounter: encounters){
            encounterDTOs.add(convertToDTO(encounter));
        }
        for (Observation observation: observations){
            observationDTOs.add(convertToDTO(observation));
        }
        return new PatientDTO(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhoneNr(),
                encounterDTOs,
                observationDTOs);
    }
    public static EncounterDTO convertToDTO(Encounter encounter) {
        List<Observation> observations = encounter.getObservations();
        List<ObservationDTO> observationDTOs = new ArrayList<>();
        for (Observation observation: observations){
            observationDTOs.add(convertToDTO(observation));
        }
        return new EncounterDTO(
                encounter.getId(),
                encounter.getDateTime(),
                encounter.getPatient().getId(),
                encounter.getPractitioner().getId(),
                observationDTOs
        );
    }
    public static PractitionerDTO convertToDTO(Practitioner practitioner){
        List<Encounter> encounters = practitioner.getEncounters();
        List<EncounterDTO> encounterDTOs = new ArrayList<>();
        List<Observation> observations = practitioner.getObservations();
        List<ObservationDTO> observationDTOs = new ArrayList<>();
        for (Encounter encounter: encounters){
            encounterDTOs.add(convertToDTO(encounter));
        }
        for (Observation observation: observations){
            observationDTOs.add(convertToDTO(observation));
        }
        return new PractitionerDTO(
                practitioner.getId(),
                practitioner.getFirstName(),
                practitioner.getLastName(),
                practitioner.getPhoneNr(),
                practitioner.getRole().toString(),
                encounterDTOs,
                observationDTOs
        );
    }
    public static ObservationDTO convertToDTO(Observation observation) {
        return new ObservationDTO(
                observation.getId(),
                observation.getDescription(),
                observation.getDateTime(),
                observation.getPatient().getId(),
                observation.getPractitioner().getId(),
                observation.getEncounter().getId()
        );
    }
}