package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.Business;

import java.util.List;

@ApplicationScoped
public class BusinessDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Business> findAll() {
        return em.createQuery(
                "SELECT b FROM Business b", Business.class)
                .getResultList();
    }
}
