package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Role;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RoleDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Role> findAll() {
        return em.createQuery("SELECT r FROM Role r", Role.class)
                .getResultList();
    }

    public Role findById(UUID id) {
        return em.find(Role.class, id);
    }

    public void persist(Role role) {
        em.persist(role);
    }

    public Role merge(Role role) {
        return em.merge(role);
    }

    public void remove(Role role) {
        em.remove(role);
    }
}