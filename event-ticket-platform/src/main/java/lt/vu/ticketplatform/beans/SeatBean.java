package lt.vu.ticketplatform.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lt.vu.ticketplatform.dao.SeatDAO;
import lt.vu.ticketplatform.dao.VenueDAO;
import lt.vu.ticketplatform.entities.Seat;
import lt.vu.ticketplatform.entities.Venue;

import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
public class SeatBean {

    @Inject
    private SeatDAO seatDAO;

    @Inject
    private VenueDAO venueDAO;

    private List<Seat> seats;
    private List<Venue> venues;

    private Seat newSeat = new Seat();
    private String selectedVenueId;

    @PostConstruct
    public void init() {
        seats = seatDAO.findAll();
        venues = venueDAO.findAll();
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public List<Venue> getVenues() {
        return venues;
    }

    public Seat getNewSeat() {
        return newSeat;
    }

    public void setNewSeat(Seat newSeat) {
        this.newSeat = newSeat;
    }

    public String getSelectedVenueId() {
        return selectedVenueId;
    }

    public void setSelectedVenueId(String selectedVenueId) {
        this.selectedVenueId = selectedVenueId;
    }

    @Transactional
    public String createSeat() {
        UUID venueId = UUID.fromString(selectedVenueId);

        Venue venue = venueDAO.findById(venueId);
        newSeat.setVenue(venue);

        seatDAO.persist(newSeat);

        newSeat = new Seat();
        selectedVenueId = null;

        return "seats?faces-redirect=true";
    }
}