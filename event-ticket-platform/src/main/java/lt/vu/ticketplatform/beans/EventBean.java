package lt.vu.ticketplatform.beans;

import jakarta.transaction.Transactional;
import lt.vu.ticketplatform.dao.EventDAO;
import lt.vu.ticketplatform.dao.VenueDAO;
import lt.vu.ticketplatform.entities.Event;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lt.vu.ticketplatform.entities.Venue;

import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
public class EventBean {

    @Inject
    private EventDAO eventDAO;

    @Inject
    private VenueDAO venueDAO;

    private List<Event> events;
    private List<Venue>  venues;
    private Event newEvent = new Event();
    private String selectedVenueId;

    @PostConstruct
    public void init() {
        events = eventDAO.findAll();
        venues = venueDAO.findAll();
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

    public Event getNewEvent() {
        return newEvent;
    }

    public void setNewEvent(Event newEvent) {
        this.newEvent = newEvent;
    }

    public String getSelectedVenueId() {
        return selectedVenueId;
    }

    public void setSelectedVenueId(String selectedVenueId) {
        this.selectedVenueId = selectedVenueId;
    }

    public String getMinimumDateTime() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        return now.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    @Transactional
    public String createEvent() {
        try {
            // Validate date is in the future
            if(newEvent.getDateTime() != null && newEvent.getDateTime().isBefore(java.time.LocalDateTime.now())) {
                FacesContext.getCurrentInstance().addMessage("createEventForm:dateTime", 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Invalid Date", 
                        "Please select an upcoming date for the event."));
                return null;
            }
            
            // Basic sanity check for venue
            if(selectedVenueId == null || selectedVenueId.isEmpty()){
                return null;
            }

            Venue venue = venueDAO.findById(UUID.fromString(selectedVenueId));
            if(venue == null) {
                return null;
            }
            
            newEvent.setVenue(venue);
            eventDAO.persist(newEvent);

            events = eventDAO.findAll();

            newEvent = new Event();
            selectedVenueId = null;

            return "events?faces-redirect=true";
        } catch (Exception e) {
            return null;
        }
    }
}