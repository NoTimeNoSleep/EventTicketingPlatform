package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Ticket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TicketDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Ticket> findAll() {
        return em.createQuery(
                        "SELECT DISTINCT t FROM Ticket t " +
                                "LEFT JOIN FETCH t.event " +
                                "LEFT JOIN FETCH t.ticketType " +
                                "LEFT JOIN FETCH t.seat " +
                                "LEFT JOIN FETCH t.order " +
                                "LEFT JOIN FETCH t.qrCode",
                        Ticket.class)
                .getResultList();
    }

    public Ticket findById(UUID id) {
        return em.find(Ticket.class, id);
    }

    public void persist(Ticket ticket) {
        em.persist(ticket);
    }

    public Ticket merge(Ticket ticket) {
        return em.merge(ticket);
    }

    public void remove(Ticket ticket) {
        em.remove(ticket);
    }
}