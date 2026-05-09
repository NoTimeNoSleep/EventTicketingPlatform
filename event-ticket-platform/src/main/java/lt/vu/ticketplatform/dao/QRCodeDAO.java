package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.QRCode;
import lt.vu.ticketplatform.enums.QRCodeStatus;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class QRCodeDAO {

    @PersistenceContext
    private EntityManager em;

    public List<QRCode> findAll() {
        return em.createQuery(
                "SELECT q FROM QRCode q", QRCode.class)
                .getResultList();
    }

    public QRCode findById(UUID id) {
        return em.find(QRCode.class, id);
    }

    public List<QRCode> findByStatus(QRCodeStatus status) {
        return em.createQuery(
                "SELECT qr FROM QRCode qr " +
                        "WHERE qr.status = :status", QRCode.class)
                .setParameter("status", status)
                .getResultList();
    }

    public void persist(QRCode qr) {
        em.persist(qr);
    }

    public QRCode merge(QRCode qr) {
        return em.merge(qr);
    }

    public void remove(QRCode qr) {
        em.remove(qr);
    }
}
