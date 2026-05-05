package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Ticket;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TicketDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Ticket> findAll() {
        return em.createQuery("SELECT t FROM Ticket t", Ticket.class)
                .getResultList();
    }

    public Ticket findById(UUID id) {
        return em.find(Ticket.class, id);
    }

    public void persist(Ticket ticket) {
        em.persist(ticket);
    }
}