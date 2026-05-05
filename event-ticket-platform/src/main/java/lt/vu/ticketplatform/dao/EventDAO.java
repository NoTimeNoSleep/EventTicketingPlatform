package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Event;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class EventDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Event> findAll() {
        return em.createQuery("SELECT e FROM Event e", Event.class)
                .getResultList();
    }
}