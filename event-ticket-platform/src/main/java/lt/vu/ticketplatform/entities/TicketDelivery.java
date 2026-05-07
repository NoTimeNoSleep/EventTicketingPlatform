package lt.vu.ticketplatform.entities;

import jakarta.persistence.*;
import lt.vu.ticketplatform.enums.DeliveryStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ticket_deliveries", schema = "event_ticketing")
public class TicketDelivery {

    @Id
    @Column(nullable = false, unique = true)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "ticket_id", nullable = false,  unique = true)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public Ticket getTicket() {
        return ticket;
    }
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }
    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public LocalDateTime getSentAt() {
        return sentAt;
    }
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
