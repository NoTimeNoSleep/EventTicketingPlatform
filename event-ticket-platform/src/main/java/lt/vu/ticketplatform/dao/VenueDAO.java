package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Venue;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class VenueDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Venue> findAll() {
        return em.createQuery("SELECT v FROM Venue v", Venue.class)
                .getResultList();
    }

    public void persist(Venue venue) {
        em.persist(venue);
    }
}