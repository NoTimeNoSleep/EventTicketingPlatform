package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.InvoiceTaxLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class InvoiceTaxLineDAO {

    @PersistenceContext
    private EntityManager em;

    public List<InvoiceTaxLine> findAll() {
        return findAllWithInvoice();
    }

    public List<InvoiceTaxLine> findAllWithInvoice() {
        return em.createQuery(
                "SELECT itl FROM InvoiceTaxLine itl " +
                        "LEFT JOIN FETCH itl.invoice " +
                        "LEFT JOIN FETCH itl.invoice.order", InvoiceTaxLine.class)
                .getResultList();
    }

    public InvoiceTaxLine findById(UUID id) {
        return em.find(InvoiceTaxLine.class, id);
    }

    public boolean existsByInvoiceIdAndNameAndRateAndAmount(
            UUID invoiceId,
            String name,
            BigDecimal rate,
            BigDecimal amount
    ) {
        Long count = em.createQuery(
                        "SELECT COUNT(itl) FROM InvoiceTaxLine itl " +
                                "WHERE itl.invoice.id = :invoiceId " +
                                "AND ((:name IS NULL AND itl.name IS NULL) OR itl.name = :name) " +
                                "AND ((:rate IS NULL AND itl.rate IS NULL) OR itl.rate = :rate) " +
                                "AND ((:amount IS NULL AND itl.amount IS NULL) OR itl.amount = :amount)",
                        Long.class)
                .setParameter("invoiceId", invoiceId)
                .setParameter("name", name)
                .setParameter("rate", rate)
                .setParameter("amount", amount)
                .getSingleResult();

        return count > 0;
    }

    public void persist(InvoiceTaxLine invoiceTaxLine) {
        em.persist(invoiceTaxLine);
    }

    public InvoiceTaxLine merge(InvoiceTaxLine invoiceTaxLine) {
        return em.merge(invoiceTaxLine);
    }

    public void remove(InvoiceTaxLine invoiceTaxLine) {
        em.remove(invoiceTaxLine);
    }
}
