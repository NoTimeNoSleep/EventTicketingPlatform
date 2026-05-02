package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.UserDAO;
import lt.vu.ticketplatform.entities.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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