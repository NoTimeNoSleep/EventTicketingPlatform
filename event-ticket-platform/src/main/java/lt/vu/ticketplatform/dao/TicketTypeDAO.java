package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.TicketType;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class TicketTypeDAO {

    @PersistenceContext
    private EntityManager em;

    public List<TicketType> findAll() {
        return em.createQuery("SELECT tt FROM TicketType tt", TicketType.class)
                .getResultList();
    }
}