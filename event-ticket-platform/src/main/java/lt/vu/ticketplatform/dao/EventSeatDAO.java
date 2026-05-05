package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.EventSeat;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
}