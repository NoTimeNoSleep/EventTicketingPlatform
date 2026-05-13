package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Event;
import lt.vu.ticketplatform.entities.TicketType;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TicketTypeDAO {

    @PersistenceContext
    private EntityManager em;

    public List<TicketType> findAll() {
        return em
                .createQuery(
                        "SELECT tt FROM TicketType tt LEFT JOIN FETCH tt.event",
                        TicketType.class
                )
                .getResultList();
    }

    public TicketType findById(UUID id) {
        return em.find(TicketType.class, id);
    }

    public List<TicketType> findByEventId(String eventId) {
        return em.createQuery(
                "SELECT tt FROM TicketType tt " +
                        "WHERE tt.event.id = :eventId", TicketType.class)
                .setParameter("eventId", eventId)
                .getResultList();
    }

    public void persist(TicketType ticketType) {
        em.persist(ticketType);
    }

    public TicketType merge(TicketType ticketType) {
        return em.merge(ticketType);
    }

    public void remove(TicketType ticketType) {
        em.remove(ticketType);
    }
}