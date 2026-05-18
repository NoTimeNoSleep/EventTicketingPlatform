package lt.vu.ticketplatform.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.transaction.Transactional;
import lt.vu.ticketplatform.beans.CurrentUserBean;
import lt.vu.ticketplatform.dao.OrderDAO;
import lt.vu.ticketplatform.entities.Order;
import lt.vu.ticketplatform.entities.Ticket;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Named
@SessionScoped
public class CartBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final Duration CART_DURATION = Duration.ofMinutes(15);

    @Inject
    private OrderDAO orderDAO;

    @Inject
    private CurrentUserBean currentUserBean;

    private String checkoutEmail;

    public static class CartItem implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        private Ticket ticket;
        private BigDecimal price;

        public CartItem(Ticket ticket) {
            this.ticket = ticket;
            this.price = ticket.getTicketType().getPrice();
        }

        public Ticket getTicket() {
            return ticket;
        }

        public void setTicket(Ticket ticket) {
            this.ticket = ticket;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CartItem cartItem = (CartItem) o;
            return Objects.equals(ticket.getId(), cartItem.ticket.getId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(ticket.getId());
        }
    }

    private List<CartItem> cartItems =  new ArrayList<CartItem>();
    private Instant cartExpiresAt;

    private void startCartTimerIfNeeded() {
        if (cartExpiresAt == null && !getCartItems().isEmpty()) {
            cartExpiresAt = Instant.now().plus(CART_DURATION);
        }
    }

    private boolean isCartExpired() {
        return cartExpiresAt != null && Instant.now().isAfter(cartExpiresAt);
    }

    private void cleanupExpiredCart() {
        if (isCartExpired()) {
            cartItems.clear();
            cartExpiresAt = null;
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Your cart expired and was cleared"));
        }
    }

    public List<CartItem> getCartItems() {
        if  (cartItems == null) {
            cartItems = new ArrayList<CartItem>();
        }
        cleanupExpiredCart();
        return cartItems;
    }

    public String addToCart(Ticket ticket) {
        try {
            cleanupExpiredCart();
            CartItem cartItem = new CartItem(ticket);
            if (cartItems.contains(cartItem)) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Ticket already in cart"));
                return null;
            }
            cartItems.add(cartItem);
            startCartTimerIfNeeded();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Ticket added to cart"));
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null;
        }
    }

    public String removeFromCart(CartItem item) {
        try {
            cleanupExpiredCart();
            if  (cartItems.contains(item)) {
                cartItems.remove(item);
                if (cartItems.isEmpty()) {
                    cartExpiresAt = null;
                }
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Ticket removed from cart"));
            }  else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "CartItem does not exist"));
            }
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null;
        }
    }

    public void clearCart() {
        cartItems.clear();
        cartExpiresAt = null;
    }

    public BigDecimal getTotalPrice() {
        cleanupExpiredCart();
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItem cartItem : cartItems) {
            totalPrice = totalPrice.add(cartItem.getPrice());
        }
        return totalPrice;
    }

    public String getTimeLeft() {
        cleanupExpiredCart();
        if (cartExpiresAt == null) {
            return "00:00";
        }

        long secondsLeft = Duration.between(Instant.now(), cartExpiresAt).getSeconds();
        if (secondsLeft < 0) {
            return "00:00";
        }

        long minutes = secondsLeft / 60;
        long seconds = secondsLeft % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public long getCartExpiresAtEpochMillis() {
        cleanupExpiredCart();
        return cartExpiresAt == null ? 0L : cartExpiresAt.toEpochMilli();
    }

    public boolean isCartTimed() {
        return cartExpiresAt != null && !isCartExpired();
    }

    public String getCheckoutEmail() {
        if (currentUserBean != null && currentUserBean.isLoggedIn()) {
            return currentUserBean.getEmail();
        }

        return checkoutEmail;
    }

    public void setCheckoutEmail(String checkoutEmail) {
        this.checkoutEmail = checkoutEmail;
    }

    public boolean isCheckoutEmailRequired() {
        return currentUserBean == null || !currentUserBean.isLoggedIn();
    }

    @Transactional
    public String checkout() {
        // TODO: implement actual order creation logic and validation before checkout
        try {
            cleanupExpiredCart();

            if (getCartItems().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Your cart is empty"));
                return null;
            }

            String email = getCheckoutEmail();
            if (email == null || email.isBlank()) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email required", "Please enter your email to continue."));
                return null;
            }

            Order order = new Order();
            order.setEmail(email.trim());
            order.setSubtotal(getTotalPrice());
            order.setTaxTotal(BigDecimal.ZERO);
            order.setTotalAmount(getTotalPrice());
            orderDAO.persist(order);

            // Add a flash message so it survives the redirect
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Order created", "Your order has been created successfully. Proceed to payment."));
            facesContext.getExternalContext().getFlash().setKeepMessages(true);
            facesContext.getExternalContext().getFlash().put("selectedOrderId", order.getId().toString());

            // Clear the cart after successful checkout
            clearCart();

            checkoutEmail = null;

            return "/payments.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Checkout failed", e.getMessage()));
            return null;
        }
    }
}
