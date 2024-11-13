package JournalSystem.controller;

import JournalSystem.model.Encounter;
import JournalSystem.model.Observation;
import JournalSystem.model.Message;
import JournalSystem.model.Patient;
import JournalSystem.model.Diagnos;
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

        List<Diagnos> diagnoses = patient.getDiagnoses();
        List<DiagnosDTO> diagnosDTOs = new ArrayList<>();

        for (Encounter encounter: encounters){
            encounterDTOs.add(convertToDTO(encounter));
        }

        for (Observation observation: observations){
            observationDTOs.add(convertToDTO(observation));
        }

        for (Diagnos diagnos : diagnoses){
            diagnosDTOs.add(convertToDTO(diagnos));
        }

        return new PatientDTO(
                patient.getId(),
                patient.getEmail(),
                patient.getName(),
                patient.getPassword(),
                patient.getPhoneNr(),
                encounterDTOs,
                observationDTOs,
                diagnosDTOs
        );
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

        List<Diagnos> diagnoses = practitioner.getDiagnoses();
        List<DiagnosDTO> diagnosDTOs = new ArrayList<>();

        for (Encounter encounter: encounters){
            encounterDTOs.add(convertToDTO(encounter));
        }

        for (Observation observation: observations){
            observationDTOs.add(convertToDTO(observation));
        }

        for (Diagnos diagnos : diagnoses){
            diagnosDTOs.add(convertToDTO(diagnos));
        }

        return new PractitionerDTO(
                practitioner.getId(),
                practitioner.getEmail(),
                practitioner.getName(),
                practitioner.getPassword(),
                practitioner.getPhoneNr(),
                practitioner.getRole().toString(),
                encounterDTOs,
                observationDTOs,
                diagnosDTOs
        );
    }

    public static ObservationDTO convertToDTO(Observation observation) {
        return new ObservationDTO(
                observation.getId(),
                observation.getDescription(),
                observation.getPatient().getId(),
                observation.getPractitioner().getId(),
                observation.getEncounter().getId(),
                observation.getDiagnos().getId()
        );
    }

    public static MessageDTO convertToDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getTitle(),
                message.getText(),
                message.getDateTime(),
                message.getIsRead(),
                message.getSender().toString(),
                message.getPatient().getId(),
                message.getPractitioner().getId()
        );
    }

    public static DiagnosDTO convertToDTO(Diagnos diagnos){
        List<Observation> observations = diagnos.getObservations();
        List<ObservationDTO> observationDTOs = new ArrayList<>();

        for (Observation observation: observations){
            observationDTOs.add(convertToDTO(observation));
        }

        return new DiagnosDTO(
                diagnos.getId(),
                diagnos.getName(),
                diagnos.getDiagnosStatus().toString(),
                diagnos.getPatient().getId(),
                diagnos.getPractitioner().getId(),
                observationDTOs
        );
    }
}