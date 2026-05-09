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
        return em.find(TaxLine.class, id);
    }

    public void persist(TaxLine taxLine) {
        em.persist(taxLine);
    }

    public TaxLine update(TaxLine taxLine) {
        return em.merge(taxLine);
    }
}