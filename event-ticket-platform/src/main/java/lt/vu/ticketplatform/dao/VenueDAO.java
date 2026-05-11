package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Venue;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class VenueDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Venue> findAll() {
        return em.createQuery(
                "SELECT DISTINCT v FROM Venue v " +
                        "LEFT JOIN FETCH v.seats",
                Venue.class
        ).getResultList();
    }

    public Venue findById(UUID id) {
        return em.find(Venue.class, id);
    }

    public List<Venue> findByName(String name) {
        return em.createQuery(
                "SELECT v FROM Venue v " +
                        "WHERE v.name LIKE CONCAT('%', :name, '%')", Venue.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Venue> findByLocation(String location) {
        return em.createQuery(
                "SELECT v FROM Venue v " +
                        "WHERE v.location LIKE CONCAT('%', :location, '%')", Venue.class)
                .setParameter("location", location)
                .getResultList();
    }

    public void persist(Venue venue) {
        em.persist(venue);
    }

    public Venue merge(Venue venue) {
        return em.merge(venue);
    }

    public void remove(Venue venue) {
        em.remove(venue);
    }
}