package lt.vu.ticketplatform.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lt.vu.ticketplatform.dao.RoleDAO;
import lt.vu.ticketplatform.dao.UserDAO;
import lt.vu.ticketplatform.entities.Role;
import lt.vu.ticketplatform.entities.User;
import lt.vu.ticketplatform.enums.RoleType;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AuthService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private RoleDAO roleDAO;

    @Inject
    private PasswordService passwordService;

    public Optional<User> authenticate(String email, String rawPassword) {
        Optional<User> user = userDAO.findByEmail(email);

        if (user.isEmpty()) {
            return Optional.empty();
        }

        if (!passwordService.matches(rawPassword, user.get().getPasswordHash())) {
            return Optional.empty();
        }

        return user;
    }

    @Transactional
    public RegistrationResult register(String name, String surname, String email, String rawPassword) {
        if (userDAO.existsByEmail(email)) {
            return RegistrationResult.EMAIL_ALREADY_USED;
        }

        Role customerRole = roleDAO.findByType(RoleType.CUSTOMER)
                .orElseThrow(() -> new IllegalStateException("CUSTOMER role does not exist"));

        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email.trim());
        user.setPasswordHash(passwordService.hashPassword(rawPassword));
        user.setRoles(List.of(customerRole));

        userDAO.persist(user);

        return RegistrationResult.SUCCESS;
    }

    public enum RegistrationResult {
        SUCCESS,
        EMAIL_ALREADY_USED
    }
}