package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.Order;
import lt.vu.ticketplatform.entities.Payment;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PaymentDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Payment> findAll() {
        return em.createQuery("select p from Payment p",Payment.class)
                .getResultList();
    }

    public Payment findById(UUID id) {
        return em.find(Payment.class, id);
    }

    public List<Payment> findByOrder(Order order) {
        return em.createQuery("select p from Payment p where p.order.id = :orderId", Payment.class)
                .setParameter("orderId", order.getId())
                .getResultList();
    }

    public void persist(Payment payment) {
        em.persist(payment);
    }

    public Payment merge(Payment payment) {
        return em.merge(payment);
    }

    public void remove(Payment payment) {
        em.remove(payment);
    }
}
