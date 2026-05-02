package lt.vu.ticketplatform.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "roles", schema = "event_ticketing")
public class Role {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String type;

    public Role() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}