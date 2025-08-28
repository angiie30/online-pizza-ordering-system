package pupr.pizza;

import java.time.LocalDateTime;
import pupr.pizza.menu.*;
import pupr.pizza.model.*;
import pupr.pizza.order.*;
import pupr.pizza.service.OrderService;

// Main entry point for the application
// Showcasing the pizza ordering workflow
public class Main {
    public static void main(String[] args) {
        System.out.println("Order Pizza App");

        // 1) Seed demo data (menu + variants)
        MenuSeed menu = seedMenu();

        // 2) Create a demo customer
        Customer customer = seedCustomer();

        // 3) Build a shopping cart using seeded menu
        ShoppingCart cart = buildCart(menu, customer);
        printCart(cart);

        // 4) Create an order from the cart
        OrderService orderService = new OrderService();
        Order order = createOrderFromCart(orderService, customer, cart);

        // 5) Simulate checkout (credit card)
        Transaction tx = checkoutWithTestCard(orderService, order, customer);

        // 6) Show final results
        System.out.println("\nOrderService:");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Order Status after checkout: " + order.getLastStatus());
        System.out.println("Transaction ID: " + tx.getTransactionId());
        System.out.printf("Transaction Amount: $%.2f\n", tx.getAmount());
        System.out.println("Payment Status: " + tx.getPaymentStatus());
    }

    // ===== Demo data seeding =====

    private static MenuSeed seedMenu() {
        // Categories
        MenuCategory pizzaCategory = new MenuCategory(1, "Pizza", 1);
        MenuCategory drinksCategory = new MenuCategory(2, "Drinks", 2);

        // Items
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

        // Variants
        addVariants(margherita, new Object[][]{
            {1, margherita.getMenuItemId(), "Small", 0.0},
            {2, margherita.getMenuItemId(), "Medium", 2.0},
            {3, margherita.getMenuItemId(), "Large", 4.0}
        });

        addVariants(pepperoni, new Object[][]{
            {4, pepperoni.getMenuItemId(), "Small", 0.0},
            {5, pepperoni.getMenuItemId(), "Medium", 2.0},
            {6, pepperoni.getMenuItemId(), "Large", 4.0}
        });

        addVariants(cola, new Object[][]{
            {7, cola.getMenuItemId(), "Can", 0.0},
            {8, cola.getMenuItemId(), "Bottle", 1.5}
        });

        // Return a compact seed object with the items we need for the demo
        MenuSeed seed = new MenuSeed();
        seed.margherita = margherita;
        seed.cola = cola;
        // We'll frequently use "Medium" for Margherita in this demo
        seed.margheritaMediumVariant = margherita.getVariants().stream()
                .filter(v -> Double.compare(v.getExtraPrice(), 2.0) == 0)
                .findFirst()
                .orElse(null);
        return seed;
    }

    private static void addVariants(MenuItem item, Object[][] data) {
        for (Object[] row : data) {
            int id = (int) row[0];
            int menuItemId = (int) row[1];
            String name = (String) row[2];
            double extra = (double) row[3];
            item.addVariant(new MenuItemVariant(id, menuItemId, name, extra));
        }
    }

    private static Customer seedCustomer() {
        Address customerAddress = new Address("123 Main St", "", "San Juan", "", "00901");
        return new Customer(1L, "Alice", "alice@email.com", "7871234567", customerAddress);
    }

    // ===== Cart building & printing =====

    private static ShoppingCart buildCart(MenuSeed menu, Customer customer) {
        ShoppingCart cart = new ShoppingCart(1, Math.toIntExact(customer.getId()));

        // 1 pizza Margherita (Medium)
        double margheritaUnit = menu.margherita.getBasePrice()
                + (menu.margheritaMediumVariant != null ? menu.margheritaMediumVariant.getExtraPrice() : 0.0);
        CartItem pizzaCartItem = new CartItem(cart.getCartId(), menu.margherita, margheritaUnit);
        pizzaCartItem.setQuantity(1);

        // 2 Cola (Can)
        double colaUnit = menu.cola.getBasePrice(); // Can variant extra = 0.0
        CartItem colaCartItem = new CartItem(cart.getCartId(), menu.cola, colaUnit);
        colaCartItem.setQuantity(2);

        cart.addItem(pizzaCartItem);
        cart.addItem(colaCartItem);
        return cart;
    }

    private static void printCart(ShoppingCart cart) {
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
    }

    // ===== Order + checkout =====

    private static Order createOrderFromCart(OrderService orderService, Customer customer, ShoppingCart cart) {
        Order order = orderService.createOrder(customer, cart);
        order.updateTotal();
        order.updateTaxes();
        System.out.println("\nOrder created!");
        System.out.printf("Order total: $%.2f\n", order.getGrandTotal());
        System.out.println("Order status: " + order.getLastStatus());
        return order;
    }

    private static Transaction checkoutWithTestCard(OrderService orderService, Order order, Customer customer) {
        CreditCard card = new CreditCard(
            "pm_4111111111111111",            // paymentMethodId
            "Personal Visa",                  // paymentMethodAlias
            "1111",                           // last4DigitsOfCard
            CardBrand.VISA,                    // brand
            Math.toIntExact(customer.getId()), // userId
            true,                               // defaultPayment
            LocalDateTime.now()                 // createdAt
        );

        return orderService.checkout(
            order.getOrderId(),
            "credit_card",
            card
        );
    }

    /** Simple container for the seeded menu objects used in the demo. */
    private static class MenuSeed {
        MenuItem margherita;
        MenuItem cola;
        MenuItemVariant margheritaMediumVariant;
    }
}