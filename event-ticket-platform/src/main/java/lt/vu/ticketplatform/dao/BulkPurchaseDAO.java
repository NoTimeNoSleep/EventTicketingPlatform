package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.BulkPurchase;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BulkPurchaseDAO {

    @PersistenceContext
    private EntityManager em;

    public List<BulkPurchase> findAll() {
        return em.createQuery(
                "SELECT bp FROM BulkPurchase bp",  BulkPurchase.class)
                .getResultList();
    }

    public BulkPurchase findById(UUID id) {
        return em.find(BulkPurchase.class, id);
    }

    public void persist(BulkPurchase bulkPurchase) {
        em.persist(bulkPurchase);
    }

    public BulkPurchase merge(BulkPurchase bulkPurchase) {
        return em.merge(bulkPurchase);
    }

    public void remove(BulkPurchase bulkPurchase) {
        em.remove(bulkPurchase);
    }
}
