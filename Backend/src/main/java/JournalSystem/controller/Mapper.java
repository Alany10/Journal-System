package JournalSystem.controller;

import JournalSystem.model.*;
import JournalSystem.viewModel.*;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static UserDTO convertToDTO(User user){

        // Lista för encounters beroende på användarroll
        List<Encounter> encounters = user.getRole() == Role.PATIENT ? user.getPatientEncounters() : user.getPractitionerEncounters();
        List<EncounterDTO> encounterDTOs = new ArrayList<>();

        // Lista för observations beroende på användarroll
        List<Observation> observations = user.getRole() == Role.PATIENT ? user.getPatientObservations() : user.getPractitionerObservations();
        List<ObservationDTO> observationDTOs = new ArrayList<>();

        // Lista för diagnoser beroende på användarroll
        List<Diagnos> diagnoses = user.getRole() == Role.PATIENT ? user.getPatientDiagnoses() : user.getPractitionerDiagnoses();
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

        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                user.getPhoneNr(),
                user.getRole().toString(),
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