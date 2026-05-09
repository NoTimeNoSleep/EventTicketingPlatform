package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.EventSeatDAO;
import lt.vu.ticketplatform.entities.EventSeat;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class EventSeatBean {

    @Inject
    private EventSeatDAO eventSeatDAO;

    public List<EventSeat> getEventSeats() {
        return eventSeatDAO.findAll();
    }
}