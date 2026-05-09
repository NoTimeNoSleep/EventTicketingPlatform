package lt.vu.ticketplatform.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.ticketplatform.dao.TaxRuleDAO;
import lt.vu.ticketplatform.entities.TaxRule;

import java.util.List;

@Named
@RequestScoped
public class TaxRuleBean {

    @Inject
    private TaxRuleDAO taxRuleDAO;

    public List<TaxRule> getTaxRules() {
        return taxRuleDAO.findAll();
    }
}