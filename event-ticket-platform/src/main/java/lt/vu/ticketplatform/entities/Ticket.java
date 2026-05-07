package lt.vu.ticketplatform.entities;

import lt.vu.ticketplatform.enums.TicketStatus;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tickets", schema = "event_ticketing")
public class Ticket {

    @Id
    @Column(nullable = false, unique = true)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id", nullable = false)
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @OneToOne(mappedBy = "ticket")
    private QRCode qrCode;

    public Ticket() {
        this.id = UUID.randomUUID();
        this.status = TicketStatus.RESERVED;
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

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public QRCode getQRCode() {
        return qrCode;
    }

    public void setQRCode(QRCode qrCode) {
        this.qrCode = qrCode;
    }
}