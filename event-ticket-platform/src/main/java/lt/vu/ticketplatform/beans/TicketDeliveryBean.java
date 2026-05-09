package lt.vu.ticketplatform.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.ticketplatform.dao.TicketDeliveryDAO;
import lt.vu.ticketplatform.entities.TicketDelivery;

import java.util.List;

@Named
@RequestScoped
public class TicketDeliveryBean {

    @Inject
    private TicketDeliveryDAO ticketDeliveryDAO;

    public List<TicketDelivery> getTicketDeliveries() {
        return ticketDeliveryDAO.findAll();
    }
}
