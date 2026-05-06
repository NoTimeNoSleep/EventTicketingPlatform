package lt.vu.ticketplatform.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.ticketplatform.dao.BulkPurchaseDAO;
import lt.vu.ticketplatform.entities.BulkPurchase;

import java.util.List;

@Named
@RequestScoped
public class BulkPurchaseBean {

    @Inject
    private BulkPurchaseDAO bulkPurchaseDAO;

    public List<BulkPurchase> getBulkPurchases() {
        return bulkPurchaseDAO.findAll();
    }
}
