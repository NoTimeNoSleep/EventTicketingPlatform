package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.TaxRule;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TaxRuleDAO {

    @PersistenceContext
    private EntityManager em;

    public List<TaxRule> findAll() {
        return em.createQuery("SELECT tr FROM TaxRule tr", TaxRule.class)
                .getResultList();
    }

    public TaxRule findById(UUID id) {
        return em.find(TaxRule.class, id);
    }

    public void persist(TaxRule taxRule) {
        em.persist(taxRule);
    }

    public TaxRule update(TaxRule taxRule) {
        return em.merge(taxRule);
    }
}