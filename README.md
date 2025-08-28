# 🍕 Online Pizza Ordering System

System implemented in **Java** that simulates an online pizza ordering process, based on an **object-oriented design** and the UML class diagram designed in Homework #2.

## 📌 Objective

The system allows:

- Creating and managing pizza orders.
- Adding menu items with categories, variants, and prices.
- Handling a shopping cart with multiple items and customizations.
- Processing payments (cash or credit card).
- Assigning employees to orders and controlling permissions.
- Managing order states (_Pending, In Progress, Completed, Cancelled_).

## 📂 Project Structure

```
src/
 └── pupr/pizza/
     ├── model/          # Core entities (Customer, Employee, Address, etc.)
     ├── menu/           # Menu classes (Menu, MenuCategory, MenuItem, MenuItemVariant, etc.)
     ├── order/          # Order, OrderDetail, ShoppingCart, CartItem, Payment, Transaction, CreditCard
     ├── security/       # AuthorizationService, Permission, Role
     └── service/        # OrderService and domain logic
```

## 🛠️ Technologies

- **Language:** Java 17+
- **Recommended IDEs:** Visual Studio Code / IntelliJ IDEA
- **Paradigm:** Object-Oriented Programming (OOP)
- **Design model:** UML Class Diagram (Homework #2 → Homework #3)

## ▶️ How to Run

1. Clone the repository:
   ```bash
   git clone <repo-url>
   cd ordering_pizza
   ```
2. Compile the project:
   ```bash
   javac -d bin src/main/java/**/*.java
   ```
3. Run the example (`Main.java`):
   ```bash
   java -cp bin pupr.pizza.Main
   ```

## 📖 Usage Example

```java
// Seed menu with categories, items, and variants
MenuCategory pizzaCategory = new MenuCategory(1, "Pizza", 1);
MenuItem margherita = new MenuItem("Margherita", 8.0);
margherita.setMenuItemId(1);
margherita.addVariant(new MenuItemVariant(2, margherita.getMenuItemId(), "Medium", 2.0));

MenuCategory drinksCategory = new MenuCategory(2, "Drinks", 2);
MenuItem cola = new MenuItem("Cola", 2.0);
cola.setMenuItemId(3);
cola.addVariant(new MenuItemVariant(7, cola.getMenuItemId(), "Can", 0.0));

// Create customer
Address customerAddress = new Address("123 Main St", "", "San Juan", "", "00901");
Customer customer = new Customer(1L, "Alice", "alice@email.com", "7871234567", customerAddress);

// Build shopping cart
ShoppingCart cart = new ShoppingCart(1, Math.toIntExact(customer.getId()));
CartItem pizzaCartItem = new CartItem(cart.getCartId(), margherita, margherita.getBasePrice() + 2.0); // Medium
pizzaCartItem.setQuantity(1);
CartItem colaCartItem = new CartItem(cart.getCartId(), cola, cola.getBasePrice()); // Can
colaCartItem.setQuantity(2);
cart.addItem(pizzaCartItem);
cart.addItem(colaCartItem);

// Create order from cart using OrderService
OrderService orderService = new OrderService();
Order order = orderService.createOrder(customer, cart);
order.updateTotal();
order.updateTaxes();

// Checkout with a credit card
CreditCard card = new CreditCard(
    "pm_4111111111111111", "Personal Visa", "1111", CardBrand.VISA,
    Math.toIntExact(customer.getId()), true, LocalDateTime.now()
);
Transaction tx = orderService.checkout(order.getOrderId(), "credit_card", card);
```

## ✅ Features Implemented

- Order creation with multiple details.
- Shopping cart with item-level prices and totals.
- Transaction processing with different payment methods.
- Authorization of operations based on employee roles.
- Full control of the order state flow.
- Support for menu categories and item variants.
