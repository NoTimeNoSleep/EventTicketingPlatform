package lt.vu.ticketplatform.entities;

import jakarta.persistence.*;
import java.util.UUID;
import lt.vu.ticketplatform.enums.RoleType;

@Entity
@Table(name = "roles", schema = "event_ticketing")
public class Role {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleType type;

    public Role() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }
}