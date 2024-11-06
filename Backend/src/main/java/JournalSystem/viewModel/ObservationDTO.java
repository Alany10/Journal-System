package JournalSystem.viewModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObservationDTO {
    private int id;
    private String description;
    private LocalDateTime dateTime;
    private int patientId;
    private int practitionerId;
    private int encounterId;
}
