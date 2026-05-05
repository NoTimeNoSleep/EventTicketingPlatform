package lt.vu.ticketplatform.entities;

import jakarta.persistence.*;
import lt.vu.ticketplatform.enums.QRCodeStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "qr_codes", schema = "event_ticketing")
public class QRCode {

    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "ticket_id", nullable = false, unique = true)
    private Ticket ticket;

    @Column(name = "code_value", nullable = false, unique = true)
    private String codeValue;

    @Column(name = "generated_at",  nullable = false)
    private LocalDateTime generatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private QRCodeStatus status;

    public QRCode() {
        this.ticket = null;
        this.codeValue = "";
        this.generatedAt = LocalDateTime.now();
        this.status = null;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }
    public String getCodeValue() { return codeValue; }
    public void setCodeValue(String codeValue) { this.codeValue = codeValue; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
    public QRCodeStatus getStatus() { return status; }
    public void setStatus(QRCodeStatus status) { this.status = status; }
}
