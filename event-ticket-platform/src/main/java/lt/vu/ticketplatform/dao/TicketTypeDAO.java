package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.TicketType;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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