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
        return em.createQuery(
                "SELECT o FROM TicketDelivery AS o",  TicketDelivery.class)
                .getResultList();
    }

    public TicketDelivery findById(UUID id) {
        return em.find(TicketDelivery.class, id);
    }

    public List<TicketDelivery> findByEmail(String email) {
        return em.createQuery(
                "SELECT td FROM TicketDelivery td " +
                        "WHERE td.email = :email", TicketDelivery.class)
                .setParameter("email", email)
                .getResultList();
    }

    public List<TicketDelivery> findByUserID(String userId) {
        return em.createQuery(
                "SELECT td FROM TicketDelivery td " +
                        "WHERE td.user.id = :userId", TicketDelivery.class)
                .setParameter("userId", userId)
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
