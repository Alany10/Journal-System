package JournalSystem.viewModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EncounterDTO {
    private int id;
    private LocalDateTime dateTime;
    private int patientId;
    private int practitionerId;
    private List<ObservationDTO> observations;

    public EncounterDTO(int id, int patientId, int practitionerId) {
        this.id = id;
        this.patientId = patientId;
        this.practitionerId = practitionerId;
        this.observations = new ArrayList<>();
    }
}
