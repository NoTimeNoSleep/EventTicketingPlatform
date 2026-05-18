package lt.vu.ticketplatform.beans;

import lt.vu.ticketplatform.dao.OrderDAO;
import lt.vu.ticketplatform.entities.Order;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
public class OrderBean {

    @Inject
    private OrderDAO orderDAO;

    private String selectedOrderId;
    private Order selectedOrder;

    public List<Order> getOrders() {
        return orderDAO.findAll();
    }

    public void loadSelectedOrder() {
        selectedOrder = null;
        if (selectedOrderId == null || selectedOrderId.isBlank()) {
            return;
        }

        try {
            selectedOrder = orderDAO.findById(UUID.fromString(selectedOrderId));
        } catch (IllegalArgumentException e) {
            selectedOrder = null;
        }
    }

    public String getSelectedOrderId() {
        return selectedOrderId;
    }

    public void setSelectedOrderId(String selectedOrderId) {
        this.selectedOrderId = selectedOrderId;
    }

    public Order getSelectedOrder() {
        return selectedOrder;
    }
}
