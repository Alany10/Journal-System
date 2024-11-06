package JournalSystem.viewModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import JournalSystem.model.Role;

<<<<<<< Updated upstream
=======
import java.util.ArrayList;
import java.util.List;

>>>>>>> Stashed changes
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PractitionerDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNr;
<<<<<<< Updated upstream
    private Role role;
=======
    private String role;
    private List<EncounterDTO> encounters;
    private List<ObservationDTO> observation;

    public PractitionerDTO(int id, String firstName, String lastName, String phoneNr, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNr = phoneNr;
        this.role = role;
        this.encounters = new ArrayList<>();
        this.observation = new ArrayList<>();
    }
>>>>>>> Stashed changes
}
