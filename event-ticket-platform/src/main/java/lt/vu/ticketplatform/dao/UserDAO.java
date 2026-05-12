package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    public List<User> findAll() {
        return em.createQuery(
                "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles",
                User.class
        ).getResultList();
    }

    public User findById(UUID id) {
        return em.find(User.class, id);
    }

    public Optional<User> findByEmail(String email) {
        try {
            User user = em.createQuery(
                            "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE LOWER(u.email) = LOWER(:email)",
                            User.class
                    )
                    .setParameter("email", email)
                    .getSingleResult();

            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public boolean existsByEmail(String email) {
        Long count = em.createQuery(
                        "SELECT COUNT(u) FROM User u WHERE LOWER(u.email) = LOWER(:email)",
                        Long.class
                )
                .setParameter("email", email)
                .getSingleResult();

        return count > 0;
    }

    public void persist(User user) {
        em.persist(user);
    }

    public User merge(User user) {
        return em.merge(user);
    }

    public void remove(User user) {
        em.remove(user);
    }
}