package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.SeatDAO;
import lt.vu.ticketplatform.entities.Seat;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class SeatBean {

    @Inject
    private SeatDAO seatDAO;

    private List<Seat> seats;

    @PostConstruct
    public void init() {
        seats = seatDAO.findAll();
    }

    public List<Seat> getSeats() {
        return seats;
    }
}