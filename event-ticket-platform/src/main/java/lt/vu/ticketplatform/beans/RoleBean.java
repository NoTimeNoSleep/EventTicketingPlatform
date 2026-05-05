package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.RoleDAO;
import lt.vu.ticketplatform.entities.Role;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class RoleBean {

    @Inject
    private RoleDAO roleDAO;

    private List<Role> roles;

    @PostConstruct
    public void init() {
        roles = roleDAO.findAll();
    }

    public List<Role> getRoles() {
        return roles;
    }
}