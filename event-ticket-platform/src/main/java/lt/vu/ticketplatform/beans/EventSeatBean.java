package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.EventSeatDAO;
import lt.vu.ticketplatform.entities.EventSeat;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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