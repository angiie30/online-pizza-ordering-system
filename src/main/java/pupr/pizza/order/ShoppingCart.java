package pupr.pizza.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private int cartId;
    private int customerId;
    private double taxes;
    private double total;
    private CheckoutStatus checkoutStatus;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private final List<CartItem> items;

    public ShoppingCart(int cartId, int customerId) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.checkoutStatus = CheckoutStatus.OPEN;
        this.items = new ArrayList<>();
    }

    public void checkout(int cartId) {
        this.checkoutStatus = CheckoutStatus.CHECKED_OUT;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public CheckoutStatus getCheckoutStatus() {
        return checkoutStatus;
    }

    public void setCheckoutStatus(CheckoutStatus checkoutStatus) {
        this.checkoutStatus = checkoutStatus;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(CartItem item) {
        this.items.add(item);
        updateTotal();
        updateTaxes();
        this.updatedAt = LocalDateTime.now();
    }

    public void removeItem(int menuItemId) {
        this.items.removeIf(i -> i.getMenuItem().getMenuItemId() == menuItemId);
        updateTotal();
        updateTaxes();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTotal() {
        this.total = items.stream().mapToDouble(CartItem::getLineTotal).sum();
    }

    public void updateTaxes() {
        this.taxes = this.total * 0.07; // Example 7% tax
    }
}