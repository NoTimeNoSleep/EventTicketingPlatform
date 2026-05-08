package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.InvoiceTaxLine;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class InvoiceTaxLineDAO {

    @PersistenceContext
    private EntityManager em;

    public List<InvoiceTaxLine> findAll() {
        return em.createQuery(
                "SELECT itl FROM InvoiceTaxLine itl LEFT JOIN FETCH itl.invoice",
                InvoiceTaxLine.class
        ).getResultList();
    }

    public InvoiceTaxLine findById(UUID id) {
        return em.find(InvoiceTaxLine.class, id);
    }

    public void persist(InvoiceTaxLine invoiceTaxLine) {
        em.persist(invoiceTaxLine);
    }

    public InvoiceTaxLine update(InvoiceTaxLine invoiceTaxLine) {
        return em.merge(invoiceTaxLine);
    }
}