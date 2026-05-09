package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
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

    public User findByEmail(String email) {
        return em.find(User.class, email);
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