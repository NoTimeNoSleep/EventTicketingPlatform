package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Seat;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.Venue;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SeatDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Seat> findAll() {
        return em.createQuery("SELECT s FROM Seat s", Seat.class)
                .getResultList();
    }

    public List<Seat> findByVenue(Venue venue) {
        return em.createQuery("select s from Seat s where s.venue.id = :venueId", Seat.class)
                .setParameter("venueId", venue.getId())
                .getResultList();
    }

    public List<Seat> findBySection(Venue venue, String section) {
        return em.createQuery("select s from Seat s where s.venue.id = :venueId and s.section = :section", Seat.class)
                .setParameter("venueId", venue.getId())
                .setParameter("section", section)
                .getResultList();
    }

    public List<Seat> findByRow(Venue venue, String row) {
        return em.createQuery("select s from Seat s where s.venue.id = :venueId and s.row = :row", Seat.class)
            .setParameter("venueId", venue.getId())
                .setParameter("row", row)
                .getResultList();
    }

    public Seat findById(UUID id) {
        return em.find(Seat.class, id);
    }

    public void persist(Seat seat) {
        em.persist(seat);
    }

    public Seat merge(Seat seat) {
        return em.merge(seat);
    }

    public void remove(Seat seat) {
        em.remove(seat);
    }
}