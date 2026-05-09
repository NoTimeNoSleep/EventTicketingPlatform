package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.EventSeat;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.Invoice;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EventSeatDAO {

    @PersistenceContext
    private EntityManager em;

    public List<EventSeat> findAll() {
        return em.createQuery("SELECT es FROM EventSeat es", EventSeat.class)
                .getResultList();
    }

    public EventSeat findById(UUID id) {
        return em.find(EventSeat.class, id);
    }

    public void persist(EventSeat eventSeat) {
        em.persist(eventSeat);
    }

    public Invoice merge(Invoice invoice) {
        return em.merge(invoice);
    }

    public void remove(EventSeat eventSeat) {
        em.remove(eventSeat);
    }
}