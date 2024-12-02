package search.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import search.model.Encounter;
import search.model.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class EncounterRepository implements PanacheRepositoryBase<Encounter, Integer> {

    // Hitta encounters för en läkare under ett specifikt datum
    public Uni<List<Encounter>> findEncountersByPractitionerAndDate(User practitioner, LocalDateTime date) {
        // Hämta alla encounters som tillhör läkaren och som matchar dagen
        return Encounter.find("practitioner = ?1 and dateTime between ?2 and ?3", practitioner,
                date.toLocalDate().atStartOfDay(), date.toLocalDate().plusDays(1).atStartOfDay()).list();
    }

    // Hitta encounters för en viss patient
    public Uni<List<Encounter>> findEncountersByPatient(User patient) {
        return Encounter.find("patient = ?1", patient).list();
    }
}
