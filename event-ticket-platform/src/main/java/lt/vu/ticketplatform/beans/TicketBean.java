package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.OrderDAO;
import lt.vu.ticketplatform.dao.SeatDAO;
import lt.vu.ticketplatform.dao.TicketDAO;
import lt.vu.ticketplatform.dao.TicketTypeDAO;
import lt.vu.ticketplatform.entities.Order;
import lt.vu.ticketplatform.entities.Seat;
import lt.vu.ticketplatform.entities.Ticket;
import lt.vu.ticketplatform.entities.TicketType;
import lt.vu.ticketplatform.enums.TicketStatus;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
public class TicketBean {

    @Inject
    private TicketDAO ticketDAO;

    @Inject
    private TicketTypeDAO ticketTypeDAO;

    @Inject
    private SeatDAO seatDAO;

    @Inject
    private OrderDAO orderDAO;

    private List<Ticket> tickets;
    private List<TicketType> ticketTypes;
    private List<Seat> seats;
    private List<Order> orders;

    private Ticket newTicket = new Ticket();

    private String selectedTicketTypeId;
    private String selectedSeatId;
    private String selectedOrderId;

    @PostConstruct
    public void init() {
        tickets = ticketDAO.findAll();
        ticketTypes = ticketTypeDAO.findAll();
        seats = seatDAO.findAll();
        orders = orderDAO.findAll();
    }

    @Transactional
    public String createTicket() {
        TicketType selectedTicketType = ticketTypeDAO.findById(UUID.fromString(selectedTicketTypeId));
        Order selectedOrder = orderDAO.findById(UUID.fromString(selectedOrderId));

        newTicket.setTicketType(selectedTicketType);
        newTicket.setEvent(selectedTicketType.getEvent());
        newTicket.setOrder(selectedOrder);

        if (selectedSeatId != null && !selectedSeatId.isBlank()) {
            Seat selectedSeat = seatDAO.findById(UUID.fromString(selectedSeatId));
            newTicket.setSeat(selectedSeat);
        }

        ticketDAO.persist(newTicket);

        return "tickets?faces-redirect=true";
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<TicketType> getTicketTypes() {
        return ticketTypes;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Ticket getNewTicket() {
        return newTicket;
    }

    public void setNewTicket(Ticket newTicket) {
        this.newTicket = newTicket;
    }

    public String getSelectedTicketTypeId() {
        return selectedTicketTypeId;
    }

    public void setSelectedTicketTypeId(String selectedTicketTypeId) {
        this.selectedTicketTypeId = selectedTicketTypeId;
    }

    public String getSelectedSeatId() {
        return selectedSeatId;
    }

    public void setSelectedSeatId(String selectedSeatId) {
        this.selectedSeatId = selectedSeatId;
    }

    public String getSelectedOrderId() {
        return selectedOrderId;
    }

    public void setSelectedOrderId(String selectedOrderId) {
        this.selectedOrderId = selectedOrderId;
    }

    public TicketStatus[] getTicketStatuses() {
        return TicketStatus.values();
    }
}