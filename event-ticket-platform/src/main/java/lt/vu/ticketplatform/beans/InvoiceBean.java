package lt.vu.ticketplatform.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
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

    private Invoice newInvoice = new Invoice();
    private String selectedOrderId;

    @PostConstruct
    public void init() {
        invoices = invoiceDAO.findAll();
        orders = orderDAO.findAll();

        newInvoice.setIssuedAt(LocalDateTime.now());
        newInvoice.setStatus(InvoiceStatus.DRAFT);
    }

    @Transactional
    public String createInvoice() {
        Order selectedOrder = orderDAO.findById(UUID.fromString(selectedOrderId));
        newInvoice.setOrder(selectedOrder);

        invoiceDAO.persist(newInvoice);

        return "invoices?faces-redirect=true";
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Invoice getNewInvoice() {
        return newInvoice;
    }

    public void setNewInvoice(Invoice newInvoice) {
        this.newInvoice = newInvoice;
    }

    public String getSelectedOrderId() {
        return selectedOrderId;
    }

    public void setSelectedOrderId(String selectedOrderId) {
        this.selectedOrderId = selectedOrderId;
    }

    public InvoiceStatus[] getInvoiceStatuses() {
        return InvoiceStatus.values();
    }
}