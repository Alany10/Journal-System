package search.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import search.model.Diagnos;
import search.model.DiagnosStatus;
import search.model.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class DiagnosRepository implements PanacheRepositoryBase<Diagnos, Integer> {

    // Hitta diagnoser baserat på patient
    public Uni<List<Diagnos>> findDiagnosesByPatient(User patient) {
        return Diagnos.find("patient = ?1", patient).list();
    }

    // Hitta diagnoser baserat på status
    public Uni<List<Diagnos>> findDiagnosesByStatus(String status) {
        return Diagnos.find("diagnosStatus", DiagnosStatus.valueOf(status)).list();
    }

    // Hitta diagnoser för en viss läkare
    public Uni<List<Diagnos>> findDiagnosesByPractitioner(User practitioner) {
        return Diagnos.find("practitioner = ?1", practitioner).list();
    }
}
