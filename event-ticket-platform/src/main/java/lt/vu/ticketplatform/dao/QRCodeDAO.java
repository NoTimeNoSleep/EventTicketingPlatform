package lt.vu.ticketplatform.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.vu.ticketplatform.entities.QRCode;

import java.util.List;

@ApplicationScoped
public class QRCodeDAO {

    @PersistenceContext
    private EntityManager em;

    public List<QRCode> findAll() {
        return em.createQuery("SELECT q FROM QRCode q", QRCode.class)
                .getResultList();
    }
}
