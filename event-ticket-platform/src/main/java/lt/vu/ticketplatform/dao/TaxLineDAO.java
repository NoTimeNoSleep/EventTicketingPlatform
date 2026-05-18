package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.TaxLine;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TaxLineDAO {

    @PersistenceContext
    private EntityManager em;

    public List<TaxLine> findAll() {
        return em.createQuery(
                "SELECT tl FROM TaxLine tl " +
                        "LEFT JOIN FETCH tl.order " +
                        "LEFT JOIN FETCH tl.ticket",
                TaxLine.class
        ).getResultList();
    }

    public TaxLine findById(UUID id) {
        return em.createQuery(
                "SELECT tl FROM TaxLine tl " +
                        "LEFT JOIN FETCH tl.order " +
                        "LEFT JOIN FETCH tl.ticket " +
                        "WHERE tl.id = :id",
                TaxLine.class
        ).setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public List<TaxLine> findByOrderId(UUID orderId) {
        return em.createQuery(
                "SELECT tl FROM TaxLine tl " +
                        "LEFT JOIN FETCH tl.order " +
                        "LEFT JOIN FETCH tl.ticket " +
                        "WHERE tl.order.id = :orderId",
                TaxLine.class
        ).setParameter("orderId", orderId)
                .getResultList();
    }

    public void persist(TaxLine taxLine) {
        em.persist(taxLine);
    }

    public TaxLine merge(TaxLine taxLine) {
        return em.merge(taxLine);
    }

    public void remove(TaxLine taxLine) {
        em.remove(taxLine);
    }
}
