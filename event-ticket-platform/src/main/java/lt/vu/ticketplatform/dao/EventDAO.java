package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Event;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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