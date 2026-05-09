package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.Seat;
import lt.vu.ticketplatform.entities.TicketDelivery;
import lt.vu.ticketplatform.entities.User;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TicketDeliveryDAO {

    @PersistenceContext
    private EntityManager em;

    public List<TicketDelivery> findAll() {
        return em.createQuery("select o from TicketDelivery as o",  TicketDelivery.class)
                .getResultList();
    }

    public TicketDelivery findById(UUID id) {
        return em.find(TicketDelivery.class, id);
    }

    public List<TicketDelivery> findByEmail(String email) {
        return em.createQuery("select td from TicketDelivery td where td.email = :email", TicketDelivery.class)
                .setParameter("email", email)
                .getResultList();
    }

    public List<TicketDelivery> findByUser(User user) {
        return em.createQuery("select td from TicketDelivery td where td.user.id = :userId", TicketDelivery.class)
                .setParameter("userId", user.getId())
                .getResultList();
    }

    public void persist(TicketDelivery ticketDelivery) {
        em.persist(ticketDelivery);
    }

    public TicketDelivery merge(TicketDelivery ticketDelivery) {
        return em.merge(ticketDelivery);
    }

    public void remove(TicketDelivery ticketDelivery) {
        em.remove(ticketDelivery);
    }
}
