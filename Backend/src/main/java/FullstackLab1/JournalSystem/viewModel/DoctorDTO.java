package FullstackLab1.JournalSystem.viewModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private int id; // Patientens ID
    private String firstName; // Patientens förnamn
    private String lastName; // Patientens efternamn
    private String phoneNr; // Patientens telefonnummer
}
