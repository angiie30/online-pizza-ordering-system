package pupr.pizza;

import java.time.LocalDateTime;
import pupr.pizza.menu.*;
import pupr.pizza.model.*;
import pupr.pizza.order.*;
import pupr.pizza.service.OrderService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Order Pizza App");

        // 1. Crear categorías
        MenuCategory pizzaCategory = new MenuCategory(1, "Pizza", 1);
        MenuCategory drinksCategory = new MenuCategory(2, "Drinks", 2);

        // 2. Crear items
        MenuItem margherita = new MenuItem("Margherita", 8.0);
        margherita.setMenuItemId(1);
        margherita.setDescription("Classic Margherita Pizza");
        margherita.setMenuCategoryId(pizzaCategory.getMenuCategoryId());

        MenuItem pepperoni = new MenuItem("Pepperoni", 9.0);
        pepperoni.setMenuItemId(2);
        pepperoni.setDescription("Pepperoni Pizza");
        pepperoni.setMenuCategoryId(pizzaCategory.getMenuCategoryId());

        MenuItem cola = new MenuItem("Cola", 2.0);
        cola.setMenuItemId(3);
        cola.setDescription("Cola Drink");
        cola.setMenuCategoryId(drinksCategory.getMenuCategoryId());

        // 3. Crear variantes y agregarlas a los items
        MenuItemVariant small = new MenuItemVariant(1, margherita.getMenuItemId(), "Small", 0.0);
        MenuItemVariant medium = new MenuItemVariant(2, margherita.getMenuItemId(), "Medium", 2.0);
        MenuItemVariant large = new MenuItemVariant(3, margherita.getMenuItemId(), "Large", 4.0);

        margherita.addVariant(small);
        margherita.addVariant(medium);
        margherita.addVariant(large);

        pepperoni.addVariant(new MenuItemVariant(4, pepperoni.getMenuItemId(), "Small", 0.0));
        pepperoni.addVariant(new MenuItemVariant(5, pepperoni.getMenuItemId(), "Medium", 2.0));
        pepperoni.addVariant(new MenuItemVariant(6, pepperoni.getMenuItemId(), "Large", 4.0));

        cola.addVariant(new MenuItemVariant(7, cola.getMenuItemId(), "Can", 0.0));
        cola.addVariant(new MenuItemVariant(8, cola.getMenuItemId(), "Bottle", 1.5));

        // 4. Crear usuarios
        Address customerAddress = new Address("123 Main St", "", "San Juan", "", "00901");
        Customer customer = new Customer(1L, "Alice", "alice@email.com", "7871234567", customerAddress);

        // 5. Simular carrito de compras
        ShoppingCart cart = new ShoppingCart(1, Math.toIntExact(customer.getId()));

        // Añadir items al carrito (ejemplo: 1 pizza mediana y 2 latas de cola)
        CartItem pizzaCartItem = new CartItem(cart.getCartId(), margherita, margherita.getBasePrice() + medium.getExtraPrice());
        pizzaCartItem.setQuantity(1);

        CartItem colaCartItem = new CartItem(cart.getCartId(), cola, cola.getBasePrice() + 0.0); // Can variant, extraPrice=0
        colaCartItem.setQuantity(2);

        cart.addItem(pizzaCartItem);
        cart.addItem(colaCartItem);

        System.out.println("\nShopping Cart:");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%s x%d - $%.2f (unit: $%.2f)\n",
                    item.getMenuItem().getName(),
                    item.getQuantity(),
                    item.getLineTotal(),
                    item.getUnitPrice());
        }
        System.out.printf("Subtotal: $%.2f\n", cart.getTotal());
        System.out.printf("Taxes: $%.2f\n", cart.getTaxes());

        // 6. Simular checkout (crear Order y mostrar total)
        OrderService orderService = new OrderService();
        Order order = orderService.createOrder(customer, cart);
        order.updateTotal();
        order.updateTaxes();

        System.out.println("\nOrder created!");
        System.out.printf("Order total: $%.2f\n", order.getGrandTotal());
        System.out.println("Order status: " + order.getLastStatus());

        // Simular checkout (el cliente paga)
        CreditCard card = new CreditCard(
            "pm_4111111111111111",           // paymentMethodId
            "Personal Visa",                 // paymentMethodAlias
            "1111",                          // last4DigitsOfCard
            CardBrand.VISA,                  // brand
            Math.toIntExact(customer.getId()), // userId
            true,                            // defaultPayment
            LocalDateTime.now()              // createdAt
        );

        Transaction tx = orderService.checkout(
            order.getOrderId(),
            "credit_card",
            card
        );

        System.out.println("\nOrderService:");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Order Status after checkout: " + order.getLastStatus());
        System.out.println("Transaction ID: " + tx.getTransactionId());
        System.out.printf("Transaction Amount: $%.2f\n", tx.getAmount());
        System.out.println("Payment Status: " + tx.getPaymentStatus());
    }
}