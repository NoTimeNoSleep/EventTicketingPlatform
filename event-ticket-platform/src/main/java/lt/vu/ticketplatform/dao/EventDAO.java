package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Event;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EventDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Event> findAll() {
        return em.createQuery("SELECT e FROM Event e", Event.class)
                .getResultList();
    }

    public Event findById(UUID id) {
        return em.find(Event.class, id);
    }

    public Event findByName(String eventName) {
        return em.find(Event.class, eventName);
    }

    public void persist(Event event) {
        em.persist(event);
    }

    public Event merge(Event event) {
        return em.merge(event);
    }

    public void remove(Event event) {
        em.remove(event);
    }
}