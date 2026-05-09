package lt.vu.ticketplatform.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.ticketplatform.dao.QRCodeDAO;
import lt.vu.ticketplatform.entities.QRCode;

import java.util.List;

@Named("qrCodeBean")
@RequestScoped
public class QRCodeBean {

    @Inject
    private QRCodeDAO qrCodeDAO;

    private List<QRCode> qrCodes;

    @PostConstruct
    public void init() {
        qrCodes = qrCodeDAO.findAll();
    }

    public List<QRCode> getQrCodes() {
        return qrCodes;
    }
}
