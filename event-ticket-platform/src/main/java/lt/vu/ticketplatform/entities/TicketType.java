package lt.vu.ticketplatform.entities;

import lt.vu.ticketplatform.enums.TicketKind;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ticket_types", schema = "event_ticketing")
public class TicketType {

    @Id
    @Column(nullable = false, unique = true)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketKind type;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private Integer capacity;

    @Column(name = "reserved_count", nullable = false)
    private Integer reserved;

    @Column(name = "sold_count", nullable = false)
    private Integer sold;

    @OneToMany(mappedBy = "ticketType")
    private List<EventSeat> eventSeats;

    @OneToMany(mappedBy = "ticketType")
    private List<Ticket> tickets;

    public TicketType() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TicketKind getType() {
        return type;
    }

    public void setType(TicketKind type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getReserved() {
        return reserved;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public List<EventSeat> getEventSeats() {
        return eventSeats;
    }

    public void setEventSeats(List<EventSeat> eventSeats) {
        this.eventSeats = eventSeats;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}