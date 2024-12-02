package search.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import search.model.Diagnos;
import search.model.Observation;

import java.util.List;

@ApplicationScoped
public class ObservationRepository implements PanacheRepositoryBase<Observation, Integer> {

    // Hitta observationer för en diagnos
    public Uni<List<Observation>> findObservationsByDiagnos(Diagnos diagnos) {
        return Observation.find("diagnos = ?1", diagnos).list();
    }
}
