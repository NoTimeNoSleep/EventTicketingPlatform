package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.Payment;

import java.util.List;

@ApplicationScoped
public class PaymentDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Payment> findAll() {
        return em.createQuery("select p from Payment p",Payment.class)
                .getResultList();
    }
}
