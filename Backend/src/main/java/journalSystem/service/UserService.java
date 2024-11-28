package journalSystem.service;

import journalSystem.model.User;
import journalSystem.model.Role;
import journalSystem.repository.IUserRepository;
import journalSystem.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is taken");
        }
        return userRepository.save(user);
    }


    @Override
    public User updateUser(int id, User user) {
        User existingUser = getUserById(id);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNr(user.getPhoneNr());
        existingUser.setRole(user.getRole());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    @Override
    public int getIdByEmail(String email) {
        return userRepository.getIdByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        System.out.println(email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public List<User> getAllDoctors() {
        return userRepository.findAllDoctors();
    }

    @Override
    public List<User> getAllPatients() {
        return userRepository.findAllPatients();
    }

    @Override
    public List<User> getAllOthers() {
        return userRepository.findAllOthers();
    }

    @Override
    public List<User> getAllPractitioners() {
        return userRepository.findAllByRoleIn(List.of(Role.DOCTOR, Role.OTHER));
    }

    @Override
    public User getDoctorById(int id) {
        return userRepository.findByIdAndRole(id, Role.DOCTOR)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    @Override
    public User getPatientById(int id) {
        return userRepository.findByIdAndRole(id, Role.PATIENT)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    @Override
    public User getOtherById(int id) {
        return userRepository.findByIdAndRole(id, Role.OTHER)
                .orElseThrow(() -> new RuntimeException("Other not found"));
    }

    @Override
    public User getPractitionerById(int id) {
        return userRepository.findByIdAndRoleIn(id, List.of(Role.DOCTOR, Role.OTHER))
                .orElseThrow(() -> new RuntimeException("Practitioner not found"));
    }

    @Override
    public List<User> getUsersByName(String name) {
        return userRepository.findAllByName(name);
    }

    @Override
    public int countAllUsers() {
        return (int) userRepository.count();
    }
}