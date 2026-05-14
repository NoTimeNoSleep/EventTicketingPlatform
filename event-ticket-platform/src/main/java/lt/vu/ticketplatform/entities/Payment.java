package lt.vu.ticketplatform.entities;

import jakarta.persistence.*;
import lt.vu.ticketplatform.enums.PaymentMethod;
import lt.vu.ticketplatform.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments", schema = "event_ticketing")
public class Payment {

    @Id
    @Column(nullable = false, unique = true)
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "order_id",  nullable = false)
    private Order order;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    public Payment() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getPaidAt() {
        return paidAt;
    }
    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }
}
