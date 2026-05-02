package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.EventDAO;
import lt.vu.ticketplatform.entities.Event;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class EventBean {

    @Inject
    private EventDAO eventDAO;

    private List<Event> events;

    @PostConstruct
    public void init() {
        events = eventDAO.findAll();
    }

    public List<Event> getEvents() {
        return events;
    }
}