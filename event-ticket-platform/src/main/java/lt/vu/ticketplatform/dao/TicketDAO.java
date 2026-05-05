package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Ticket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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