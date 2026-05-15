package lt.vu.ticketplatform.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lt.vu.ticketplatform.dao.OrderDAO;
import lt.vu.ticketplatform.dao.TaxLineDAO;
import lt.vu.ticketplatform.dao.TicketDAO;
import lt.vu.ticketplatform.entities.Order;
import lt.vu.ticketplatform.entities.TaxLine;
import lt.vu.ticketplatform.entities.Ticket;

import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
public class TaxLineBean {

    @Inject
    private TaxLineDAO taxLineDAO;

    @Inject
    private OrderDAO orderDAO;

    @Inject
    private TicketDAO ticketDAO;

    private List<TaxLine> taxLines;
    private List<Order> orders;
    private List<Ticket> tickets;

    private TaxLine newTaxLine = new TaxLine();

    private String selectedOrderId;
    private String selectedTicketId;

    @PostConstruct
    public void init() {
        taxLines = taxLineDAO.findAll();
        orders = orderDAO.findAll();
        tickets = ticketDAO.findAll();
    }

    @Transactional
    public String createTaxLine() {
        Order selectedOrder = orderDAO.findById(UUID.fromString(selectedOrderId));
        newTaxLine.setOrder(selectedOrder);

        if (selectedTicketId != null && !selectedTicketId.isBlank()) {
            Ticket selectedTicket = ticketDAO.findById(UUID.fromString(selectedTicketId));
            newTaxLine.setTicket(selectedTicket);
        }

        taxLineDAO.persist(newTaxLine);

        return "taxLines?faces-redirect=true";
    }

    public List<TaxLine> getTaxLines() {
        return taxLines;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public TaxLine getNewTaxLine() {
        return newTaxLine;
    }

    public void setNewTaxLine(TaxLine newTaxLine) {
        this.newTaxLine = newTaxLine;
    }

    public String getSelectedOrderId() {
        return selectedOrderId;
    }

    public void setSelectedOrderId(String selectedOrderId) {
        this.selectedOrderId = selectedOrderId;
    }

    public String getSelectedTicketId() {
        return selectedTicketId;
    }

    public void setSelectedTicketId(String selectedTicketId) {
        this.selectedTicketId = selectedTicketId;
    }
}