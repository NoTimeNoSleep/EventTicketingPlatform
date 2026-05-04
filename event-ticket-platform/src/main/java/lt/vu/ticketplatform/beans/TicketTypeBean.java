package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.TicketTypeDAO;
import lt.vu.ticketplatform.entities.TicketType;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class TicketTypeBean {

    @Inject
    private TicketTypeDAO ticketTypeDAO;

    private List<TicketType> ticketTypes;

    @PostConstruct
    public void init() {
        ticketTypes = ticketTypeDAO.findAll();
    }

    public List<TicketType> getTicketTypes() {
        return ticketTypes;
    }
}