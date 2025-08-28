package pupr.pizza.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pupr.pizza.model.OrderStatus;
import pupr.pizza.model.OrderType;

/**
 * Order entity aligned with the OrderService methods and the UML.
 * Operations:
 *  - getTotal(int orderId): double   // placeholder per UML (calculation delegated elsewhere)
 */
public class Order {
    private int orderId;
    private OrderType orderType;
    private int customerId;
    private OrderStatus lastStatus;
    private String transactionId;
    private int rating;
    private String review;
    private LocalDateTime reviewAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime createdAt;
    private int assignedEmployeeId;

    private List<OrderTracking> trackingHistory = new ArrayList<>();

    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Order() { }

    public Order(int customerId) {
        this.customerId = customerId;
        this.createdAt = LocalDateTime.now();
        addTrackingStatus(OrderStatus.CREATED);
    }

    public Order(int customerId, OrderType orderType) {
        this.customerId = customerId;
        this.orderType = orderType;
        this.createdAt = LocalDateTime.now();
        addTrackingStatus(OrderStatus.CREATED);
    }

    // ===== getters & setters =====
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public OrderType getOrderType() { return orderType; }
    public void setOrderType(OrderType orderType) { this.orderType = orderType; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public OrderStatus getLastStatus() { return lastStatus; }
    public void setLastStatus(OrderStatus lastStatus) { this.lastStatus = lastStatus; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    /** For compatibility with OrderService.checkout(...) */
    public void setTransactionIdHash(String transactionId) { this.transactionId = transactionId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public LocalDateTime getReviewAt() { return reviewAt; }
    public void setReviewAt(LocalDateTime reviewAt) { this.reviewAt = reviewAt; }

    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public int getAssignedEmployeeId() { return assignedEmployeeId; }
    public void setAssignedEmployeeId(int assignedEmployeeId) { this.assignedEmployeeId = assignedEmployeeId; }

    public List<OrderTracking> getTrackingHistory() { return trackingHistory; }
    public void setTrackingHistory(List<OrderTracking> trackingHistory) {
        this.trackingHistory = (trackingHistory == null) ? new ArrayList<>() : trackingHistory;
    }

    public List<OrderDetail> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = (orderDetails == null) ? new ArrayList<>() : orderDetails;
    }

    public void addDetail(OrderDetail detail) {
        if (detail != null) {
            this.orderDetails.add(detail);
        }
    }

    public boolean removeDetailById(int orderDetailId) {
        return this.orderDetails.removeIf(d -> d != null && d.getOrderDetailId() == orderDetailId);
    }

    public void clearDetails() {
        this.orderDetails.clear();
    }

    private void addTrackingStatus(OrderStatus status) {
        if (status == null) return;
        OrderTracking tracking = new OrderTracking(this.orderId, status);
        this.trackingHistory.add(tracking);
        this.lastStatus = status;
    }

    // ===== operations per UML =====
    /**
     * Compute the grand total of the order by summing each OrderDetail total.
     * Ignores the orderId parameter; kept for UML compatibility.
     */
    public double getTotal() {
        if (orderDetails == null || orderDetails.isEmpty()) return 0.0;
        return orderDetails.stream()
                .filter(d -> d != null)
                .mapToDouble(OrderDetail::getTotal)
                .sum();
    }
    public double getTotal(int orderId) {
        return getTotal();
    }
}