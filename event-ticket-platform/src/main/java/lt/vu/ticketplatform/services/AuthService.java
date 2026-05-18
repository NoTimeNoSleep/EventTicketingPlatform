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
            return RegistrationResult.emailAlreadyUsed();
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

        return RegistrationResult.success(user);
    }

    public static class RegistrationResult {
        private final boolean success;
        private final User user;

        private RegistrationResult(boolean success, User user) {
            this.success = success;
            this.user = user;
        }

        public static RegistrationResult success(User user) {
            return new RegistrationResult(true, user);
        }

        public static RegistrationResult emailAlreadyUsed() {
            return new RegistrationResult(false, null);
        }

        public boolean isSuccess() {
            return success;
        }

        public User getUser() {
            return user;
        }
    }
}
