package lt.vu.ticketplatform.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lt.vu.ticketplatform.dao.InvoiceDAO;
import lt.vu.ticketplatform.dao.InvoiceTaxLineDAO;
import lt.vu.ticketplatform.dao.OrderDAO;
import lt.vu.ticketplatform.dao.TaxLineDAO;
import lt.vu.ticketplatform.entities.Invoice;
import lt.vu.ticketplatform.entities.InvoiceTaxLine;
import lt.vu.ticketplatform.entities.Order;
import lt.vu.ticketplatform.entities.TaxLine;
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

    @Inject
    private TaxLineDAO taxLineDAO;

    @Inject
    private InvoiceTaxLineDAO invoiceTaxLineDAO;

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

        Order selectedOrder = orderDAO.findById(orderId);
        if (selectedOrder == null) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Selected order was not found.");
            return null;
        }

        boolean existingInvoice = invoiceDAO.existsByOrderId(orderId);
        Invoice invoice = existingInvoice ? invoiceDAO.findByOrderId(orderId) : createInvoice(selectedOrder);

        List<TaxLine> taxLines = taxLineDAO.findByOrderId(orderId);
        int createdTaxLineCount = 0;
        for (TaxLine taxLine : taxLines) {
            if (existingInvoice && invoiceTaxLineDAO.existsByInvoiceIdAndNameAndRateAndAmount(
                    invoice.getId(),
                    taxLine.getName(),
                    taxLine.getRate(),
                    taxLine.getAmount()
            )) {
                continue;
            }

            InvoiceTaxLine invoiceTaxLine = new InvoiceTaxLine();
            invoiceTaxLine.setInvoice(invoice);
            invoiceTaxLine.setName(taxLine.getName());
            invoiceTaxLine.setRate(taxLine.getRate());
            invoiceTaxLine.setAmount(taxLine.getAmount());
            invoiceTaxLineDAO.persist(invoiceTaxLine);
            createdTaxLineCount++;
        }

        invoices = invoiceDAO.findAllWithOrder();
        selectedOrderId = null;
        if (taxLines.isEmpty()) {
            addMessage(FacesMessage.SEVERITY_WARN, "Invoice generated, but no tax lines were found for this order.");
        } else if (existingInvoice && createdTaxLineCount == 0) {
            addMessage(FacesMessage.SEVERITY_INFO, "Invoice already exists and all tax lines are already copied.");
        } else {
            addMessage(
                    FacesMessage.SEVERITY_INFO,
                    existingInvoice
                            ? "Invoice already existed. Added " + createdTaxLineCount + " missing tax line(s)."
                            : "Invoice generated successfully."
            );
        }

        return null;
    }

    private Invoice createInvoice(Order selectedOrder) {
        Invoice invoice = new Invoice();
        invoice.setOrder(selectedOrder);
        invoice.setSubtotal(selectedOrder.getSubtotal());
        invoice.setTaxTotal(selectedOrder.getTaxTotal());
        invoice.setTotalAmount(selectedOrder.getTotalAmount());
        invoice.setIssuedAt(LocalDateTime.now());
        invoice.setStatus(InvoiceStatus.ISSUED);

        invoiceDAO.persist(invoice);
        return invoice;
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
