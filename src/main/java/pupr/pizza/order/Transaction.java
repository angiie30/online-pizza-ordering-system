package pupr.pizza.order;

import java.time.LocalDateTime;
import pupr.pizza.model.CreditCard;

/**
 * Transaction entity aligned with OrderService.checkout and the UML.
 */
public class Transaction {
    private String transactionId;
    private String paymentMethodId;
    private String paymentIntent;
    private String paymentStatus;
    private double amount;
    private String currency;
    private LocalDateTime createdAt;
    private CreditCard creditCard;

    public Transaction() { }

    public Transaction(String transactionId, String paymentMethodId, String paymentIntent, String paymentStatus,
                       double amount, String currency, CreditCard creditCard) {
        this.transactionId = transactionId;
        this.paymentMethodId = paymentMethodId;
        this.paymentIntent = paymentIntent;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.currency = currency;
        this.creditCard = creditCard;
        this.createdAt = LocalDateTime.now();
    }

    // ===== getters & setters =====
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(String paymentMethodId) { this.paymentMethodId = paymentMethodId; }

    public CreditCard getCreditCard() { return creditCard; }
    public void setCreditCard(CreditCard creditCard) { this.creditCard = creditCard; }

    public String getPaymentIntent() { return paymentIntent; }
    public void setPaymentIntent(String paymentIntent) { this.paymentIntent = paymentIntent; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

}