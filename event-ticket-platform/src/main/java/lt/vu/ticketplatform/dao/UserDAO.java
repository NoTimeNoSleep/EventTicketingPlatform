package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }
}