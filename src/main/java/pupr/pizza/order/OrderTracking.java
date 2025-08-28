package pupr.pizza.order;

import java.time.LocalDateTime;
import pupr.pizza.model.OrderStatus;

public class OrderTracking {
    private int orderId;
    private OrderStatus orderStatus; 
    private LocalDateTime createdDate;

    public OrderTracking(int orderId, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.createdDate = LocalDateTime.now();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void addOrderStatus(int orderId, OrderStatus status) {
        this.orderId = orderId;
        this.orderStatus = status;
        this.createdDate = LocalDateTime.now();
    }
}