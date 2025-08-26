package pupr.pizza.model;

import java.time.LocalDateTime;
import java.util.Objects;

// This class represents a credit card payment method, similar to how payment methods are handled in Stripe.
public class CreditCard {
    // Fields per UML diagram
    private String paymentMethodId;       // unique id for stored payment method
    private String paymentMethodAlias;    // user-defined alias (e.g., "Personal Visa")
    private String last4DigitsOfCard;     // only last 4 digits for display
    private CardBrand brand;              // e.g., VISA, MASTERCARD
    private int userId;                   // owner user id
    private boolean defaultPayment;       // is this the default method?
    private LocalDateTime createdAt;      // when it was added to the system

    public CreditCard() {}

    public CreditCard(String paymentMethodId,
                      String paymentMethodAlias,
                      String last4DigitsOfCard,
                      CardBrand brand,
                      int userId,
                      boolean defaultPayment,
                      LocalDateTime createdAt) {
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodAlias = paymentMethodAlias;
        this.last4DigitsOfCard = last4DigitsOfCard;
        this.brand = brand;
        this.userId = userId;
        this.defaultPayment = defaultPayment;
        this.createdAt = createdAt;
    }

    public String getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(String paymentMethodId) { this.paymentMethodId = paymentMethodId; }

    public String getPaymentMethodAlias() { return paymentMethodAlias; }
    public void setPaymentMethodAlias(String paymentMethodAlias) { this.paymentMethodAlias = paymentMethodAlias; }

    public String getLast4DigitsOfCard() { return last4DigitsOfCard; }
    public void setLast4DigitsOfCard(String last4DigitsOfCard) { this.last4DigitsOfCard = last4DigitsOfCard; }

    public CardBrand getBrand() { return brand; }
    public void setBrand(CardBrand brand) { this.brand = brand; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public boolean isDefaultPayment() { return defaultPayment; }
    public void setDefaultPayment(boolean defaultPayment) { this.defaultPayment = defaultPayment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCard)) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(paymentMethodId, that.paymentMethodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentMethodId);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "paymentMethodId='" + paymentMethodId + '\'' +
                ", alias='" + paymentMethodAlias + '\'' +
                ", last4='" + last4DigitsOfCard + '\'' +
                ", brand=" + brand +
                ", userId=" + userId +
                ", defaultPayment=" + defaultPayment +
                ", createdAt=" + createdAt +
                '}';
    }
}