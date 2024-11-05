package JournalSystem.viewModel;

import JournalSystem.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNr;
}
