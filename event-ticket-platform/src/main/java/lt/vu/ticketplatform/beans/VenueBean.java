package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.VenueDAO;
import lt.vu.ticketplatform.entities.Venue;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class VenueBean {

    @Inject
    private VenueDAO venueDAO;

    private List<Venue> venues;

    @PostConstruct
    public void init() {
        venues = venueDAO.findAll();
    }

    public List<Venue> getVenues() {
        return venues;
    }
}