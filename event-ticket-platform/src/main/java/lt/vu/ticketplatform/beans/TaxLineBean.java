package lt.vu.ticketplatform.beans;

import jakarta.annotation.PostConstruct;
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

    private List<TaxLine> taxLines;

    @PostConstruct
    public void init() {
        taxLines = taxLineDAO.findAll();
    }

    public List<TaxLine> getTaxLines() {
        return taxLines;
    }
}
