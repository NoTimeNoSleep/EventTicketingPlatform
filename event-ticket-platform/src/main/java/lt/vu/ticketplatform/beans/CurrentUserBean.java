package lt.vu.ticketplatform.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lt.vu.ticketplatform.entities.User;
import lt.vu.ticketplatform.enums.RoleType;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class CurrentUserBean implements Serializable {

    private UUID id;
    private String name;
    private String surname;
    private String email;
    private Set<RoleType> roles;

    public void logIn(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.roles = user.getRoles()
                .stream()
                .map(role -> role.getType())
                .collect(Collectors.toSet());
    }

    public String logOut() {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();

        return "/index.xhtml?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return id != null;
    }

    public boolean hasRole(RoleType roleType) {
        return roles != null && roles.contains(roleType);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Set<RoleType> getRoles() {
        return roles;
    }

    public List<RoleType> getVisibleRoles() {
        if (roles == null) {
            return List.of();
        }

        return roles.stream()
                .filter(role -> role != RoleType.CUSTOMER)
                .toList();
    }
}
