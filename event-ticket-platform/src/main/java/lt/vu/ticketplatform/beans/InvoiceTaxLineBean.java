package lt.vu.ticketplatform.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.ticketplatform.dao.InvoiceTaxLineDAO;
import lt.vu.ticketplatform.entities.InvoiceTaxLine;

import java.util.List;

@Named
@RequestScoped
public class InvoiceTaxLineBean {

    @Inject
    private InvoiceTaxLineDAO invoiceTaxLineDAO;

    public List<InvoiceTaxLine> getInvoiceTaxLines() {
        return invoiceTaxLineDAO.findAllWithInvoice();
    }
}
