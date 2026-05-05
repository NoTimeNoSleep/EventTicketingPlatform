package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Seat;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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