package JournalSystem.viewModel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PractitionerDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNr;
    private String role;
    private List<EncounterDTO> encounters;
    private List<ObservationDTO> observations;
    private List<DiagnosDTO> diagnoses;

    public PractitionerDTO(int id, String firstName, String lastName, String phoneNr, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNr = phoneNr;
        this.role = role;
        this.encounters = new ArrayList<>();
        this.observations = new ArrayList<>();
        this.diagnoses = new ArrayList<>();
    }
}