package lt.vu.ticketplatform.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lt.vu.ticketplatform.dao.OrderDAO;
import lt.vu.ticketplatform.dao.UserDAO;
import lt.vu.ticketplatform.entities.Order;
import lt.vu.ticketplatform.entities.User;

import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
public class OrderBean {

    @Inject
    private OrderDAO orderDAO;

    @Inject
    private UserDAO userDAO;

    private List<Order> orders;
    private List<User> users;
    private Order newOrder = new Order();
    private String selectedUserId;

    @PostConstruct
    public void init() {
        orders = orderDAO.findAll();
        users = userDAO.findAll();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<User> getUsers() {
        return users;
    }

    public Order getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(Order newOrder) {
        this.newOrder = newOrder;
    }

    public String getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(String selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    @Transactional
    public String createOrder() {
        try {
            if (selectedUserId == null || selectedUserId.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage("createOrderForm:user",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "User required",
                                "Please select a user."));
                return null;
            }

            User user = userDAO.findById(UUID.fromString(selectedUserId));
            if (user == null) {
                return null;
            }

            newOrder.setUser(user);
            orderDAO.persist(newOrder);

            orders = orderDAO.findAll();
            newOrder = new Order();
            selectedUserId = null;

            return "orders?faces-redirect=true";
        } catch (Exception e) {
            return null;
        }
    }
}