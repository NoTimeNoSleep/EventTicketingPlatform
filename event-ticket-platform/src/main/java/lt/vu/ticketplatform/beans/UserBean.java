package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.UserDAO;
import lt.vu.ticketplatform.entities.User;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class UserBean {

    @Inject
    private UserDAO userDAO;

    private List<User> users;

    @PostConstruct
    public void init() {
        users = userDAO.findAll();
    }

    public List<User> getUsers() {
        return users;
    }
}