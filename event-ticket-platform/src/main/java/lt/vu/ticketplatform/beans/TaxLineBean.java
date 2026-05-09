package lt.vu.ticketplatform.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.ticketplatform.dao.TaxLineDAO;
import lt.vu.ticketplatform.entities.TaxLine;

import java.util.List;

@Named
@RequestScoped
public class TaxLineBean {

    @Inject
    private TaxLineDAO taxLineDAO;

    public List<TaxLine> getTaxLines() {
        return taxLineDAO.findAll();
    }
}