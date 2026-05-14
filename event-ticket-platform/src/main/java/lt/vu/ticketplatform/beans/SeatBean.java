package lt.vu.ticketplatform.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lt.vu.ticketplatform.dao.SeatDAO;
import lt.vu.ticketplatform.dao.VenueDAO;
import lt.vu.ticketplatform.entities.Seat;
import lt.vu.ticketplatform.entities.Venue;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private Map<UUID, Long> seatCounts = new HashMap<>();

    private String selectedVenueId;
    private Venue selectedVenue;
    private String section;
    private Integer rowStart;
    private Integer rowEnd;
    private Integer seatsPerRow;

    @PostConstruct
    public void init() {
        venues = venueDAO.findAll();
        loadSeatCounts();

        selectedVenueId = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("venueId");

        if (selectedVenueId == null || selectedVenueId.isBlank()) {
            seats = Collections.emptyList();
            return;
        }

        try {
            UUID venueId = UUID.fromString(selectedVenueId);
            selectedVenue = venueDAO.findById(venueId);
            seats = selectedVenue == null ? Collections.emptyList() : seatDAO.findByVenueId(venueId);
        } catch (IllegalArgumentException e) {
            selectedVenue = null;
            seats = Collections.emptyList();
        }
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public List<Venue> getVenues() {
        return venues;
    }

    public Map<UUID, Long> getSeatCounts() {
        return seatCounts;
    }

    public String getSelectedVenueId() {
        return selectedVenueId;
    }

    public void setSelectedVenueId(String selectedVenueId) {
        this.selectedVenueId = selectedVenueId;
    }

    public Venue getSelectedVenue() {
        return selectedVenue;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getRowStart() {
        return rowStart;
    }

    public void setRowStart(Integer rowStart) {
        this.rowStart = rowStart;
    }

    public Integer getRowEnd() {
        return rowEnd;
    }

    public void setRowEnd(Integer rowEnd) {
        this.rowEnd = rowEnd;
    }

    public Integer getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(Integer seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public boolean isVenueSelected() {
        return selectedVenue != null;
    }

    @Transactional
    public String createSeats() {
        if (selectedVenue == null && selectedVenueId != null && !selectedVenueId.isBlank()) {
            try {
                selectedVenue = venueDAO.findById(UUID.fromString(selectedVenueId));
            } catch (IllegalArgumentException e) {
                selectedVenue = null;
            }
        }

        if (selectedVenue == null) {
            addMessage("Please open seat management from a venue.");
            return null;
        }

        if (!validateSeatGenerationForm()) {
            return null;
        }

        String trimmedSection = section.trim();
        Set<String> existingSeatKeys = new HashSet<>();

        for (Seat seat : seatDAO.findByVenueId(selectedVenue.getId())) {
            existingSeatKeys.add(seatKey(seat.getSection(), seat.getRow(), seat.getNumber()));
        }

        int generatedCount = 0;
        int skippedCount = 0;

        for (int row = rowStart; row <= rowEnd; row++) {
            for (int number = 1; number <= seatsPerRow; number++) {
                String rowValue = String.valueOf(row);
                String numberValue = String.valueOf(number);
                String seatKey = seatKey(trimmedSection, rowValue, numberValue);

                if (existingSeatKeys.contains(seatKey)) {
                    skippedCount++;
                    continue;
                }

                Seat seat = new Seat();
                seat.setVenue(selectedVenue);
                seat.setSection(trimmedSection);
                seat.setRow(rowValue);
                seat.setNumber(numberValue);
                seatDAO.persist(seat);

                existingSeatKeys.add(seatKey);
                generatedCount++;
            }
        }

        seats = seatDAO.findByVenueId(selectedVenue.getId());
        section = null;
        rowStart = null;
        rowEnd = null;
        seatsPerRow = null;

        if (generatedCount > 0) {
            addInfoMessage(generatedCount + " seats generated.");
        } else {
            addMessage("No new seats were generated because all matching seats already exist.");
        }

        if (skippedCount > 0) {
            addInfoMessage(skippedCount + " duplicate seats skipped.");
        }

        return null;
    }

    private void addMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
    }

    private void addMessage(String clientId, String message) {
        FacesContext.getCurrentInstance().addMessage(clientId,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
    }

    private void addInfoMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
    }

    private void loadSeatCounts() {
        seatCounts.clear();

        for (Venue venue : venues) {
            seatCounts.put(venue.getId(), venueDAO.countSeats(venue.getId()));
        }
    }

    private boolean validateSeatGenerationForm() {
        boolean valid = true;

        if (section == null || section.isBlank()) {
            addMessage("seatForm:section", "Section is required.");
            valid = false;
        }

        if (rowStart == null || rowStart < 1) {
            addMessage("seatForm:rowStart", "Row start must be at least 1.");
            valid = false;
        }

        if (rowEnd == null || rowEnd < 1) {
            addMessage("seatForm:rowEnd", "Row end must be at least 1.");
            valid = false;
        }

        if (rowStart != null && rowEnd != null && rowEnd < rowStart) {
            addMessage("seatForm:rowEnd", "Row end must be greater than or equal to row start.");
            valid = false;
        }

        if (seatsPerRow == null || seatsPerRow < 1) {
            addMessage("seatForm:seatsPerRow", "Seats per row must be at least 1.");
            valid = false;
        }

        return valid;
    }

    private String seatKey(String section, String row, String number) {
        return section + "|" + row + "|" + number;
    }
}
