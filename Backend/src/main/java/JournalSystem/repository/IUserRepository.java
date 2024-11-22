package JournalSystem.repository;

import JournalSystem.model.User;
import JournalSystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    // Hämta användare baserat på email, lösenord och roll
    Optional<User> findByEmailAndPasswordAndRole(String email, String password, Role role);

    // Hämta användare baserat på email
    Optional<User> findByEmail(String email);

    // Hämta användare baserat på id och roll
    Optional<User> findByIdAndRole(int id, Role role);

    // Hämta användare baserat på id och flera roller
    Optional<User> findByIdAndRoleIn(int id, List<Role> roles);

    // Hämta ID baserat på email
    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    int getIdByEmail(String email);

    // Hämta alla användare med en specifik roll
    List<User> findAllByRole(Role role);

    // Hämta alla användare med en av rollerna
    List<User> findAllByRoleIn(List<Role> roles);

    // Kontrollera om en användare med en viss email existerar
    boolean existsByEmail(String email);

    // Hämta användare baserat på namn (fall för att hitta användare med samma namn)
    List<User> findAllByName(String name);

    // Hämta alla läkare
    default List<User> findAllDoctors() {
        return findAllByRole(Role.DOCTOR);
    }

    // Hämta alla patienter
    default List<User> findAllPatients() {
        return findAllByRole(Role.PATIENT);
    }

    // Hämta alla andra roller (OTHER)
    default List<User> findAllOthers() {
        return findAllByRole(Role.OTHER);
    }
}
