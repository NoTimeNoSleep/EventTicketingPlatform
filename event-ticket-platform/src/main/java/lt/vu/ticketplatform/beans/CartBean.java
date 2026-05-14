package lt.vu.ticketplatform.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lt.vu.ticketplatform.entities.Ticket;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Named
@SessionScoped
public class CartBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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

    public List<CartItem> getCartItems() {
        if  (cartItems == null) {
            cartItems = new ArrayList<CartItem>();
        }
        return cartItems;
    }

    public String addToCart(Ticket ticket) {
        try {
            CartItem cartItem = new CartItem(ticket);
            if (cartItems.contains(cartItem)) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Ticket already in cart"));
                return null;
            }
            cartItems.add(cartItem);
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
            if  (cartItems.contains(item)) {
                cartItems.remove(item);
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Ticket removed from cart"));
                return null;
            }  else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "CartItem does not exist"));
                return null;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null;
        }
    }

    public void clearCart() {
        cartItems.clear();
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItem cartItem : cartItems) {
            totalPrice = totalPrice.add(cartItem.getPrice());
        }
        return totalPrice;
    }

    public String checkout() {
        // TODO: implement actual logic and validation before checkout
        return "/orderConfirmation.xhtml?faces-redirect=true";
    }
}
