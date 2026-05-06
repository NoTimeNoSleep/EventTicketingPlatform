package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.BulkPurchase;
import lt.vu.ticketplatform.enums.BulkType;

import java.util.List;

@ApplicationScoped
public class BulkPurchaseDAO {

    @PersistenceContext
    private EntityManager em;

    public List<BulkPurchase> findAll() {
        return em.createQuery("select bp from BulkPurchase bp",  BulkPurchase.class)
                .getResultList();
    }
}
