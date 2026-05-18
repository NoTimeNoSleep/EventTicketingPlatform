package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.Event;
import lt.vu.ticketplatform.entities.Venue;

import java.util.List;

@ApplicationScoped
public class SearchDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Event> searchEvents(String query) {
        return em.createQuery("""
                SELECT e
                FROM Event e
                JOIN FETCH e.venue
                WHERE LOWER(e.name) LIKE LOWER(:query)
                   OR LOWER(e.description) LIKE LOWER(:query)
                   OR LOWER(e.venue.name) LIKE LOWER(:query)
                   OR LOWER(e.venue.location) LIKE LOWER(:query)
                ORDER BY e.dateTime
                """, Event.class)
                .setParameter("query", "%" + query + "%")
                .getResultList();
    }

    public List<Venue> searchVenues(String query) {
        return em.createQuery("""
                SELECT v
                FROM Venue v
                WHERE LOWER(v.name) LIKE LOWER(:query)
                   OR LOWER(v.location) LIKE LOWER(:query)
                ORDER BY v.name
                """, Venue.class)
                .setParameter("query", "%" + query + "%")
                .getResultList();
    }
}