package lt.vu.ticketplatform.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.ticketplatform.dao.PaymentDAO;
import lt.vu.ticketplatform.entities.Payment;

import java.util.List;

@Named
@RequestScoped
public class PaymentBean {

    @Inject
    private PaymentDAO paymentDAO;

    private List<Payment> payments;

    @PostConstruct
    public void init(){
        payments = paymentDAO.findAll();
    }

    public List<Payment> getPayments(){
        return payments;
    }
}
