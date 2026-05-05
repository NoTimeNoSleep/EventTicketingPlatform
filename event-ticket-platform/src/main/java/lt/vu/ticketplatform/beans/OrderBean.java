package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.OrderDAO;
import lt.vu.ticketplatform.entities.Order;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class OrderBean {

    @Inject
    private OrderDAO orderDAO;

    public List<Order> getOrders() {
        return orderDAO.findAll();
    }
}