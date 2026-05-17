package lt.vu.ticketplatform.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lt.vu.ticketplatform.dao.InvoiceDAO;
import lt.vu.ticketplatform.dao.OrderDAO;
import lt.vu.ticketplatform.entities.Invoice;
import lt.vu.ticketplatform.entities.Order;
import lt.vu.ticketplatform.enums.InvoiceStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
public class InvoiceBean {

    @Inject
    private InvoiceDAO invoiceDAO;

    @Inject
    private OrderDAO orderDAO;

    private List<Invoice> invoices;
    private List<Order> orders;

    private String selectedOrderId;

    @PostConstruct
    public void init() {
        invoices = invoiceDAO.findAllWithOrder();
        orders = orderDAO.findAll();
    }

    @Transactional
    public String generateInvoiceFromOrder() {
        if (selectedOrderId == null || selectedOrderId.isBlank()) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Please select an order.");
            return null;
        }

        UUID orderId;
        try {
            orderId = UUID.fromString(selectedOrderId);
        } catch (IllegalArgumentException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Selected order is invalid.");
            return null;
        }

        if (invoiceDAO.existsByOrderId(orderId)) {
            addMessage(FacesMessage.SEVERITY_ERROR, "This order already has an invoice.");

            return null;
        }

        Order selectedOrder = orderDAO.findById(orderId);
        if (selectedOrder == null) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Selected order was not found.");
            return null;
        }

        Invoice invoice = new Invoice();
        invoice.setOrder(selectedOrder);
        invoice.setSubtotal(selectedOrder.getSubtotal());
        invoice.setTaxTotal(selectedOrder.getTaxTotal());
        invoice.setTotalAmount(selectedOrder.getTotalAmount());
        invoice.setIssuedAt(LocalDateTime.now());
        invoice.setStatus(InvoiceStatus.ISSUED);

        invoiceDAO.persist(invoice);
        invoices = invoiceDAO.findAllWithOrder();
        selectedOrderId = null;
        addMessage(FacesMessage.SEVERITY_INFO, "Invoice generated successfully.");

        return null;
    }

    private void addMessage(FacesMessage.Severity severity, String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, null));
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public String getSelectedOrderId() {
        return selectedOrderId;
    }

    public void setSelectedOrderId(String selectedOrderId) {
        this.selectedOrderId = selectedOrderId;
    }
}
