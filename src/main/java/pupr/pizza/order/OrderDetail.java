package pupr.pizza.order;

import java.util.ArrayList;
import java.util.List;
import pupr.pizza.menu.MenuItem;

public class OrderDetail {
    private int orderDetailId;
    private int orderId;
    private MenuItem menuItem;
    private int quantity;
    private List<ItemCustomization> customizations = new ArrayList<>();

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

    public List<ItemCustomization> getCustomizations() {
        return customizations;
    }

    public void setCustomizations(List<ItemCustomization> customizations) {
        this.customizations = customizations;
    }

    public double getTotal() {
        double base = quantity * menuItem.getBasePrice();
        double customizationCost = customizations.stream()
            .mapToDouble(ItemCustomization::getExtraPrice)
            .sum() * quantity;
        return base + customizationCost;
    }
}