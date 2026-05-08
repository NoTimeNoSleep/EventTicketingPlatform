package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

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
}