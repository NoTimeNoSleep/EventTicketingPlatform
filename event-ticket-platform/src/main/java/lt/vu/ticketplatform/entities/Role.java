package lt.vu.ticketplatform.entities;

import jakarta.persistence.*;
import lt.vu.ticketplatform.enums.RoleType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "roles", schema = "event_ticketing")
public class Role {

    @Id
    @Column(nullable = false, unique = true)
    private UUID id;

    @Column(nullable = false, unique = true, columnDefinition = "event_ticketing.role_type_enum")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
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