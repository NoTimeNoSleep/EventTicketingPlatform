package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Role;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class RoleDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Role> findAll() {
        return em.createQuery("SELECT r FROM Role r", Role.class)
                .getResultList();
    }
}