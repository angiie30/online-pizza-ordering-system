package pupr.pizza.menu;

/**
 * Entity representing a variant belonging to a MenuItem.
 * \- Aggregation (add/remove) is managed from MenuItem.
 */
public class MenuItemVariant {
    private int menuItemVariantId;
    private int menuItemId; // reference to the owner
    private String name;
    private double extraPrice;

    public MenuItemVariant() { }

    public MenuItemVariant(int menuItemVariantId, int menuItemId, String name, double extraPrice) {
        this.menuItemVariantId = menuItemVariantId;
        this.menuItemId = menuItemId;
        this.name = name;
        this.extraPrice = extraPrice;
    }

    public int getMenuItemVariantId() { return menuItemVariantId; }
    public void setMenuItemVariantId(int menuItemVariantId) { this.menuItemVariantId = menuItemVariantId; }

    public int getMenuItemId() { return menuItemId; }
    public void setMenuItemId(int menuItemId) { this.menuItemId = menuItemId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getExtraPrice() { return extraPrice; }
    public void setExtraPrice(double extraPrice) { this.extraPrice = extraPrice; }
}