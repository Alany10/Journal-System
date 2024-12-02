package search.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import search.model.Diagnos;
import search.model.Encounter;
import search.model.Role;
import search.model.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, Integer> {

    // Hitta patienter baserat på namn som innehåller argumentet
    public Uni<List<User>> findPatientsByName(String name) {
        return User.find("name LIKE ?1", "%" + name + "%").list();
    }

    // Hitta läkare
    public Uni<List<User>> findDoctors() {
        return User.find("role", Role.valueOf("DOCTOR")).list();
    }

    // Hitta patienter för en viss läkare
    public Uni<List<User>> findPatientsByDoctor(User doctor) {
        return User.find("SELECT DISTINCT d.patient FROM Diagnos d WHERE d.practitioner = ?1", doctor)
                .list();
    }

    // Hitta patienter baserat på diagnos
    public Uni<List<User>> findPatientsByDiagnos(Diagnos diagnos) {
        return User.find("SELECT DISTINCT d.patient FROM Diagnos d WHERE d = ?1", diagnos).list();
    }

    // Hitta patienter baserat på encounter
    public Uni<List<User>> findPatientsByEncounter(Encounter encounter) {
        return User.find("SELECT DISTINCT e.patient FROM Encounter e WHERE e = ?1", encounter).list();
    }
}
