package JournalSystem.viewModel;

import JournalSystem.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

<<<<<<< Updated upstream
=======
import java.util.ArrayList;
import java.util.List;

>>>>>>> Stashed changes
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNr;
<<<<<<< Updated upstream
=======
    private List<EncounterDTO> encounters;
    private List<ObservationDTO> observation;

    public PatientDTO(int id, String firstName, String lastName, String phoneNr) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNr = phoneNr;
        this.encounters = new ArrayList<>();
        this.observation = new ArrayList<>();
    }
>>>>>>> Stashed changes
}
