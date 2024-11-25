package journalSystem.viewModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObservationDTO {
    private int id;
    private String description;
    private int patientId;
    private int practitionerId;
    private int encounterId;
    private int diagnosId;
}
