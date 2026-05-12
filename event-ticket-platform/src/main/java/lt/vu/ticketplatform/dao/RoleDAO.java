package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.Role;
import lt.vu.ticketplatform.enums.RoleType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class RoleDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Role> findAll() {
        return em.createQuery(
                "SELECT r FROM Role r",
                Role.class
        ).getResultList();
    }

    public Role findById(UUID id) {
        return em.find(Role.class, id);
    }

    public Optional<Role> findByType(RoleType type) {
        try {
            Role role = em.createQuery(
                            "SELECT r FROM Role r WHERE r.type = :type",
                            Role.class
                    )
                    .setParameter("type", type)
                    .getSingleResult();

            return Optional.of(role);
        } catch (NoResultException e) {
            return Optional.empty();
        }
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