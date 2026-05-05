package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Role;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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