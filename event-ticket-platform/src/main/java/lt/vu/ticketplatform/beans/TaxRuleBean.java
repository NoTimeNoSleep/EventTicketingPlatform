package lt.vu.ticketplatform.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lt.vu.ticketplatform.dao.TaxRuleDAO;
import lt.vu.ticketplatform.entities.TaxRule;
import lt.vu.ticketplatform.enums.TaxType;

import java.util.List;

@Named
@RequestScoped
public class TaxRuleBean {

    @Inject
    private TaxRuleDAO taxRuleDAO;

    private TaxRule newTaxRule = new TaxRule();

    public TaxRuleBean() {
        newTaxRule.setIncludedInPrice(false);
    }

    public List<TaxRule> getTaxRules() {
        return taxRuleDAO.findAll();
    }

    public TaxRule getNewTaxRule() {
        return newTaxRule;
    }

    public void setNewTaxRule(TaxRule newTaxRule) {
        this.newTaxRule = newTaxRule;
    }

    public TaxType[] getTaxTypes() {
        return TaxType.values();
    }

    @Transactional
    public String createTaxRule() {
        taxRuleDAO.persist(newTaxRule);
        return "taxRules?faces-redirect=true";
    }
}
