package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.VenueDAO;
import lt.vu.ticketplatform.entities.Venue;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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