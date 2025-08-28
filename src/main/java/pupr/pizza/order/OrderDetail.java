package pupr.pizza.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pupr.pizza.menu.MenuItem;

public class OrderDetail {
    private int orderDetailId;
    private int orderId;
    private MenuItem menuItem;
    private int quantity;

    /** Base unit price of the menu item (without customizations). */
    private final double unitPrice; // frozen at add-to-cart time

    private List<ItemCustomization> customizations = new ArrayList<>();

    public OrderDetail(int orderId, MenuItem menuItem, int quantity, double unitPrice,  List<ItemCustomization> customizations) {
        this.orderDetailId = LocalDateTime.now().hashCode();
        this.orderId = orderId;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.customizations = new ArrayList<>(customizations);
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

     public double getUnitPrice() {
        return unitPrice;
    }


    public List<ItemCustomization> getCustomizations() {
        return customizations;
    }

     /** Returns the extra cost per unit from customizations. */
    public double getExtrasPerUnit() {
        return customizations.stream().mapToDouble(ItemCustomization::getExtraPrice).sum();
    }

    /** Calculates the line total considering customizations per unit and quantity. */
    public double getLineTotal() {
        return (unitPrice + getExtrasPerUnit()) * quantity;
    }

}