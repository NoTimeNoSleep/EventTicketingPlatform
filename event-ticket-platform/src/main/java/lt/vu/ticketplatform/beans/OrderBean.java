package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.OrderDAO;
import lt.vu.ticketplatform.entities.Order;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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