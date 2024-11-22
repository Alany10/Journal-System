package JournalSystem.service.interfaces;

import JournalSystem.model.User;
import JournalSystem.model.Role;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    // CRUD-operationer
    List<User> getAllUsers();                // Hämta alla användare
    User getUserById(int id);                // Hämta användare via ID
    User createUser(User user);              // Skapa en ny användare
    User updateUser(int id, User user);      // Uppdatera en användare via ID
    void deleteUser(int id);                 // Ta bort en användare via ID

    // Verifiering och existenskontroll
    boolean existsById(int id);              // Kontrollera om en användare existerar via ID
    boolean verifyLogin(String email, String password, Role role); // Verifiera inloggning

    // Hämta specifika attribut
    int getIdByEmail(String email);          // Hämta ID via email
    Optional<User> getUserByEmail(String email); // Hämta användare via email

    // Rollbaserade metoder
    List<User> getUsersByRole(Role role);    // Hämta alla användare av en viss roll
    List<User> getAllDoctors();                 // Hämta alla läkare
    List<User> getAllPatients();                // Hämta alla patienter
    List<User> getAllOthers();                  // Hämta alla användare av rollen OTHER
    List<User> getAllPractitioners();          // Hämta alla användare av rollen OTHER eller DOCTOR

    User getDoctorById(int id);
    User getPatientById(int id);
    User getOtherById(int id);
    User getPractitionerById(int id);

    // Avancerade filtreringsmetoder
    List<User> getUsersByName(String name);  // Hämta alla användare med ett specifikt namn

    int countAllUsers();                    // Räkna totala antalet användare
}
