package lt.vu.ticketplatform.dao;

import lt.vu.ticketplatform.entities.Order;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrderDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Order> findAll() {
        return em.createQuery("SELECT o FROM CustomerOrder o", Order.class)
                .getResultList();
    }

    public Order findById(UUID id) {
        return em.find(Order.class, id);
    }

    public void persist(Order order) {
        em.persist(order);
    }

    public Order merge(Order order) {
        return em.merge(order);
    }

    public void remove(Order order) {
        em.remove(order);
    }
}