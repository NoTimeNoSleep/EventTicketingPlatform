package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.TicketDAO;
import lt.vu.ticketplatform.entities.Ticket;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class TicketBean {

    @Inject
    private TicketDAO ticketDAO;

    public List<Ticket> getTickets() {
        return ticketDAO.findAll();
    }
}