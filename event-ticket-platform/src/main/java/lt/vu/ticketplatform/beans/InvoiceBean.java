package lt.vu.ticketplatform.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.ticketplatform.dao.InvoiceDAO;
import lt.vu.ticketplatform.entities.Invoice;

import java.util.List;

@Named
@RequestScoped
public class InvoiceBean {

    @Inject
    private InvoiceDAO invoiceDAO;

    public List<Invoice> getInvoices() {
        return invoiceDAO.findAll();
    }
}