package lt.vu.ticketplatform.entities;

import jakarta.persistence.*;
import lt.vu.ticketplatform.enums.BulkType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bulk_purchases", schema = "event_ticketing")
public class BulkPurchase {

    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "type",  nullable = false)
    @Enumerated(EnumType.STRING)
    private BulkType type;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_email")
    private String companyEmail;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public BulkPurchase() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

    public BulkPurchase(Order order, BulkType type) {
        this.order = order;
        this.type = type;
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
    public BulkType getType() {
        return type;
    }
    public void setType(BulkType type) {
        this.type = type;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCompanyEmail() {
        return companyEmail;
    }
    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }
    public String getTaxId() {
        return taxId;
    }
    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
