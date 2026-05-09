package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.Invoice;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class InvoiceDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Invoice> findAll() {
        return em.createQuery(
                "SELECT i FROM Invoice i LEFT JOIN FETCH i.order",
                Invoice.class
        ).getResultList();
    }

    public Invoice findById(UUID id) {
        return em.find(Invoice.class, id);
    }

    public void persist(Invoice invoice) {
        em.persist(invoice);
    }

    public Invoice merge(Invoice invoice) {
        return em.merge(invoice);
    }

    public void remove(Invoice invoice) {
        em.remove(invoice);
    }
}