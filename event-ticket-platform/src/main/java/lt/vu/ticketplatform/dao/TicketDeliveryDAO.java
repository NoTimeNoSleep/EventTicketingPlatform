package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.TicketDelivery;

import java.util.List;

@ApplicationScoped
public class TicketDeliveryDAO {

    @PersistenceContext
    private EntityManager em;

    public List<TicketDelivery> findAll() {
        return em.createQuery("select o from TicketDelivery as o",  TicketDelivery.class)
                .getResultList();
    }
}
