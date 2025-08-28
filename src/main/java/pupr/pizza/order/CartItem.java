package pupr.pizza.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pupr.pizza.menu.MenuItem;

/**
 * Represents a single line in the shopping cart for a given MenuItem.
 *
 * This class manages its own quantity (increment/decrement) and computes line totals with customizations.
 * Customizations are now represented as a list of ItemCustomization objects. The unitPrice is intentionally immutable (frozen) to preserve the historical price at the time the item was added to the cart.
 */
public class CartItem {
    private final int cartId;
    private final MenuItem menuItem;
    private int quantity;

    /** Base unit price of the menu item (without customizations). */
    private final double unitPrice; // frozen at add-to-cart time

    /** List of per-unit customizations. */
    private final List<ItemCustomization> customizations = new ArrayList<>();

    public CartItem(int cartId, MenuItem menuItem, double unitPrice) {
        if (unitPrice < 0) throw new IllegalArgumentException("unitPrice must be >= 0");
        this.cartId = cartId;
        this.menuItem = menuItem;
        this.unitPrice = unitPrice;
        this.quantity = 0; // starts empty; use addItem() to increase
    }

    /**
     * Creates a CartItem freezing the current price from the MenuItem at add-to-cart time.
     */
    public CartItem(int cartId, MenuItem menuItem) {
        if (menuItem == null) throw new IllegalArgumentException("menuItem is required");
        this.cartId = cartId;
        this.menuItem = menuItem;
        this.unitPrice = menuItem.getPrice();
        this.quantity = 0; // starts empty; use addItem()/incrementQuantity() to increase
    }

    public int getCartId() {
        return cartId;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("quantity must be >= 0");
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public List<ItemCustomization> getCustomizations() {
        return Collections.unmodifiableList(customizations);
    }

    /** Adds a customization (per unit). */
    public void addCustomization(ItemCustomization customization) {
        if (customization == null) throw new IllegalArgumentException("customization is required");
        customizations.add(customization);
    }

    /** Removes a customization instance. No-op if not present. */
    public void removeCustomization(ItemCustomization customization) {
        if (customization != null) {
            customizations.remove(customization);
        }
    }

    /** Removes a customization by id. No-op if it does not exist. */
    public void removeCustomizationById(int id) {
        customizations.removeIf(c -> c.getCustomizationId() == id);
    }

    /** Increment quantity by 1. */
    public void incrementQuantity() {
        this.quantity += 1;
    }

    /** Decrement quantity by 1 (not below zero). */
    public void decrementQuantity() {
        if (this.quantity > 0) {
            this.quantity -= 1;
        }
    }

    /**
     * Add a positive delta to the quantity.
     * @throws IllegalArgumentException if delta <= 0
     */
    public void addQuantity(int delta) {
        if (delta <= 0) throw new IllegalArgumentException("delta must be > 0");
        this.quantity += delta;
    }

    /** Returns the extra cost per unit from customizations. */
    public double getExtrasPerUnit() {
        return customizations.stream().mapToDouble(ItemCustomization::getExtraPrice).sum();
    }

    /** Calculates the line total considering customizations per unit and quantity. */
    public double getLineTotal() {
        return (unitPrice + getExtrasPerUnit()) * quantity;
    }

    /** @deprecated Use {@link #getLineTotal()} instead. */
    @Deprecated
    public double getTotalWithCustomizations() {
        return getLineTotal();
    }
}