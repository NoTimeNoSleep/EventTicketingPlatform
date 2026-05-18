package lt.vu.ticketplatform.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lt.vu.ticketplatform.dao.OrderDAO;
import lt.vu.ticketplatform.dao.PaymentDAO;
import lt.vu.ticketplatform.entities.Order;
import lt.vu.ticketplatform.entities.Payment;

import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
public class PaymentBean {

    @Inject
    private PaymentDAO paymentDAO;

    @Inject
    private OrderDAO orderDAO;

    private List<Payment> payments;
    private List<Order> orders;
    private Payment newPayment = new Payment();
    private String selectedOrderId;

    @PostConstruct
    public void init() {
        payments = paymentDAO.findAll();
        orders = orderDAO.findAll();

        Object selectedOrderIdFromFlash = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash()
                .get("selectedOrderId");

        if (selectedOrderIdFromFlash != null) {
            selectedOrderId = selectedOrderIdFromFlash.toString();
        }
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Payment getNewPayment() {
        return newPayment;
    }

    public void setNewPayment(Payment newPayment) {
        this.newPayment = newPayment;
    }

    public String getSelectedOrderId() {
        return selectedOrderId;
    }

    public void setSelectedOrderId(String selectedOrderId) {
        this.selectedOrderId = selectedOrderId;
    }

    @Transactional
    public String createPayment() {
        try {
            if (selectedOrderId == null || selectedOrderId.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage("createPaymentForm:order",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Order required",
                                "Please select an order."));
                return null;
            }

            Order order = orderDAO.findById(UUID.fromString(selectedOrderId));
            if (order == null) {
                return null;
            }

            newPayment.setOrder(order);
            paymentDAO.persist(newPayment);

            payments = paymentDAO.findAll();
            newPayment = new Payment();
            selectedOrderId = null;

            return "payments?faces-redirect=true";
        } catch (Exception e) {
            return null;
        }
    }
}