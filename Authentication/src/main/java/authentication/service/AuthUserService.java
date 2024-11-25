package authentication.service;

import authentication.model.AuthUser;
import authentication.repository.IAuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserService {

    @Autowired
    private IAuthUserRepository authUserRepository;

    public AuthUserService(IAuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    public Optional<AuthUser> findByEmail(String email) {
        return authUserRepository.findByEmail(email);
    }

    public AuthUser createUser(AuthUser user) {
        if (authUserRepository.findByEmail(user.getEmail()).isPresent())
            throw new IllegalArgumentException("User already exists");

        //TODO hasha lösenord
        return authUserRepository.save(user);
    }
}
