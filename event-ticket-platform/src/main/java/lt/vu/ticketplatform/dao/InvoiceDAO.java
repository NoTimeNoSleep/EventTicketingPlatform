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
        return findAllWithOrder();
    }

    public List<Invoice> findAllWithOrder() {
        return em.createQuery(
                "SELECT i FROM Invoice i " +
                        "LEFT JOIN FETCH i.order", Invoice.class)
                .getResultList();
    }

    public Invoice findById(UUID id) {
        return em.find(Invoice.class, id);
    }

    public Invoice findByOrderId(UUID orderId) {
        return em.createQuery(
                        "SELECT i FROM Invoice i " +
                                "LEFT JOIN FETCH i.order " +
                                "WHERE i.order.id = :orderId",
                        Invoice.class)
                .setParameter("orderId", orderId)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public boolean existsByOrderId(UUID orderId) {
        Long count = em.createQuery(
                        "SELECT COUNT(i) FROM Invoice i WHERE i.order.id = :orderId",
                        Long.class)
                .setParameter("orderId", orderId)
                .getSingleResult();

        return count > 0;
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
