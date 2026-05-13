package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.EventDAO;
import lt.vu.ticketplatform.dao.TicketTypeDAO;
import lt.vu.ticketplatform.entities.Event;
import lt.vu.ticketplatform.entities.TicketType;
import lt.vu.ticketplatform.enums.TicketKind;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
public class TicketTypeBean {

    @Inject
    private TicketTypeDAO ticketTypeDAO;

    @Inject
    private EventDAO eventDAO;

    private List<TicketType> ticketTypes;
    private List<Event> events;

    private TicketType newTicketType = new TicketType();
    private String selectedEventId;

    @PostConstruct
    public void init() {
        ticketTypes = ticketTypeDAO.findAll();
        events = eventDAO.findAll();

        newTicketType.setReserved(0);
        newTicketType.setSold(0);
    }

    @Transactional
    public String createTicketType() {
        Event selectedEvent = eventDAO.findById(UUID.fromString(selectedEventId));
        newTicketType.setEvent(selectedEvent);

        ticketTypeDAO.persist(newTicketType);

        return "ticketTypes?faces-redirect=true";
    }

    public List<TicketType> getTicketTypes() {
        return ticketTypes;
    }

    public List<Event> getEvents() {
        return events;
    }

    public TicketType getNewTicketType() {
        return newTicketType;
    }

    public void setNewTicketType(TicketType newTicketType) {
        this.newTicketType = newTicketType;
    }

    public String getSelectedEventId() {
        return selectedEventId;
    }

    public void setSelectedEventId(String selectedEventId) {
        this.selectedEventId = selectedEventId;
    }

    public TicketKind[] getTicketKinds() {
        return TicketKind.values();
    }
}