package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Seat;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class SeatDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Seat> findAll() {
        return em.createQuery("SELECT s FROM Seat s", Seat.class)
                .getResultList();
    }
}