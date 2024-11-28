package journalSystem.viewModel;


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
public class UserDTO {
    private int id;
    private String email;
    private String name;
    private String password;
    private String phoneNr;
    private String role;
    private List<EncounterDTO> encounters;
    private List<ObservationDTO> observations;
    private List<DiagnosDTO> diagnoses;

    public UserDTO(int id, String email, String name, String password, String phoneNr, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNr = phoneNr;
        this.role = role;
        this.encounters = new ArrayList<>();
        this.observations = new ArrayList<>();
        this.diagnoses = new ArrayList<>();
    }

    public UserDTO(int id, String email, String name, String phoneNr, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNr = phoneNr;
        this.role = role;
        this.encounters = new ArrayList<>();
        this.observations = new ArrayList<>();
        this.diagnoses = new ArrayList<>();
    }

    public UserDTO(int id, String email, String name, String phoneNr, String role, List<EncounterDTO> encounters, List<ObservationDTO> observations, List<DiagnosDTO> diagnoses) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNr = phoneNr;
        this.role = role;
        this.encounters = encounters;
        this.observations = observations;
        this.diagnoses = diagnoses;
    }
}