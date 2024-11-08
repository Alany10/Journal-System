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
public class DiagnosDTO {
    private int id;
    private String name;
    private String diagnosStatus;
    private int patientId;
    private int practitionerId;
    private List<ObservationDTO> observations;

    public DiagnosDTO(int id, String name, String diagnosStatus, int patientId, int practitionerId) {
        this.id = id;
        this.name = name;
        this.diagnosStatus = diagnosStatus;
        this.patientId = patientId;
        this.practitionerId = practitionerId;
        this.observations = new ArrayList<>();
    }
}