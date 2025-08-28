package pupr.pizza.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import pupr.pizza.model.CreditCard;
import pupr.pizza.model.Customer;
import pupr.pizza.model.Employee;
import pupr.pizza.model.OrderStatus;
import pupr.pizza.model.OrderType;
import pupr.pizza.order.Order; 
import pupr.pizza.order.OrderTracking;
import pupr.pizza.order.ShoppingCart;
import pupr.pizza.order.Transaction;
import pupr.pizza.security.Action;
import pupr.pizza.security.AuthorizationService;

//comments cambiar a ingles
/**
 * Minimal in-memory implementation that respects the UML:
 *  + createOrder(customer, cart): Order
 *  + assignEmployee(orderId, employeeId)
 *  + updateOrderStatus(orderId, status)
 *  + checkout(orderId, paymentMethodId, creditCard): Transaction
 *  + addReview(orderId, rating, review)
 */
public class OrderService {

    private final AuthorizationService authz;

    // In-memory storage for demo purposes (you can replace with repositories)
    private final Map<Integer, Order> orders = new HashMap<>();
    private final Map<Integer, Employee> employees = new HashMap<>();
    private final Map<String, Transaction> transactions = new HashMap<>();

    public OrderService() {
        this.authz = new AuthorizationService();
    }

    public OrderService(AuthorizationService authz) {
        this.authz = Objects.requireNonNull(authz, "authz");
    }

    public Order createOrder(Customer customer, ShoppingCart cart) {
        Order o = new Order(customer.getId().intValue(), OrderType.DELIVERY, cart);

        orders.put(o.getOrderId(), o);
        return o;
    }

    public void assignEmployee(int orderId, int employeeId, Employee actingUser) {
        require(authz.can(actingUser, Action.ASSIGN_EMPLOYEE), "Not allowed to assign employee");

        Order o = getOrderOrThrow(orderId);
        Employee e = employees.get(employeeId);
        if (e == null) throw new IllegalArgumentException("Employee not found: " + employeeId);

        o.setAssignedEmployeeId(employeeId);
    }

    public void updateOrderStatus(int orderId, OrderStatus status, Employee actingUser) {
        require(authz.can(actingUser, Action.UPDATE_ORDER_STATUS), "Not allowed to update status");

        Order o = getOrderOrThrow(orderId);
        o.setLastStatus(status);
        o.getTrackingHistory().add(new OrderTracking(orderId, status));
    }

    public Transaction checkout(int orderId, String paymentMethodId, CreditCard creditCard) {
        Order o = getOrderOrThrow(orderId);

        Transaction tx = new Transaction();
        tx.setTransactionId("TX-" + System.nanoTime());
        tx.setPaymentMethodId(paymentMethodId);
        tx.setPaymentIntent("sale");
        tx.setPaymentStatus("succeeded");
        tx.setAmount(o.getGrandTotal());
        tx.setCurrency("USD");
        tx.setCreditCard(creditCard);
        tx.setCreatedAt(LocalDateTime.now());

        transactions.put(tx.getTransactionId(), tx);

        o.setTransactionId(tx.getTransactionId());
        o.setLastStatus(OrderStatus.CONFIRMED);
        o.getTrackingHistory().add(new OrderTracking(orderId, OrderStatus.CONFIRMED));
        return tx;
    }

    public void addReview(int orderId, int rating, String review, Employee actingUser) {
        require(authz.can(actingUser, Action.ADD_REVIEW), "Not allowed to add review");

        Order o = getOrderOrThrow(orderId);
        o.setRating(rating);
        o.setReviewAt(LocalDateTime.now());
        o.setReview(review);
    }

    // ===== util =====
    /** Read a transaction by its id (uses the in-memory store). */
    public Transaction getTransaction(String transactionId) {
        return transactions.get(transactionId);
    }

    /** Expose a read-only view of all transactions (useful for tests/demos). */
    public Map<String, Transaction> getTransactions() {
        return Collections.unmodifiableMap(transactions);
    }

    private Order getOrderOrThrow(int orderId) {
        Order o = orders.get(orderId);
        if (o == null) throw new IllegalArgumentException("Order not found: " + orderId);
        return o;
    }

    private static void require(boolean cond, String msg) {
        if (!cond) throw new SecurityException(msg);
    }

    // Helpers to populate employees in the demo
    public void registerEmployee(Employee e) {
        employees.put(e.getId().intValue(), e);
    }
}