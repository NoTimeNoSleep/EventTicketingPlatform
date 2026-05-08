package lt.vu.ticketplatform.entities;

import jakarta.persistence.*;
import java.util.UUID;
import java.util.List;
import lt.vu.ticketplatform.enums.RoleType;

@Entity
@Table(name = "roles", schema = "event_ticketing")
public class Role {

    @Id
    @Column(nullable = false, unique = true)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleType type;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

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