package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.EmailJob;

import java.util.List;

@ApplicationScoped
public class EmailJobDAO {

    @PersistenceContext
    private EntityManager em;

    public List<EmailJob> findAll() {
        return em.createQuery(
                "SELECT e FROM EmailJob e " +
                        "LEFT JOIN FETCH e.notification",
                EmailJob.class
        ).getResultList();
    }
}