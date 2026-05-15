package lt.vu.ticketplatform.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
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

    public boolean isCartTimed() {
        return cartExpiresAt != null && !isCartExpired();
    }

    public String checkout() {
        // TODO: implement actual logic and validation before checkout
        return "/orderConfirmation.xhtml?faces-redirect=true";
    }
}
