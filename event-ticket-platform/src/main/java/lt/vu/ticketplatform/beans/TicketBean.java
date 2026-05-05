package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.TicketDAO;
import lt.vu.ticketplatform.entities.Ticket;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
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