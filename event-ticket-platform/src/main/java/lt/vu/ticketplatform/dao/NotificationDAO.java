package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.Notification;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class NotificationDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Notification> findAll() {
        return em.createQuery(
                "SELECT n FROM Notification n " +
                        "LEFT JOIN FETCH n.user " +
                        "LEFT JOIN FETCH n.ticketDelivery",
                Notification.class
        ).getResultList();
    }

    public Notification findById(UUID id) {
        return em.find(Notification.class, id);
    }

    public void persist(Notification notification) {
        em.persist(notification);
    }

    public Notification merge(Notification notification) {
        return em.merge(notification);
    }

    public void remove(Notification notification) {
        em.remove(notification);
    }
}