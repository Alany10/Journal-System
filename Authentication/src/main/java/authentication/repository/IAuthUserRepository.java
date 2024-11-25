package authentication.repository;

import authentication.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAuthUserRepository extends JpaRepository<AuthUser, Integer> {
    Optional<AuthUser> findByEmail(String email);
}
