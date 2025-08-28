package pupr.pizza.order;

import pupr.pizza.menu.MenuItemVariant;

/**
 * Snapshot of a chosen customization from the catalog (MenuItemVariant),
 * owned by a line (CartItem/OrderDetail).
 * Keeps a copy of name and price, and optionally the traceability
 * to the catalog via sourceMenuItemId/sourceVariantId.
 */
public class ItemCustomization {
    // Internal identifier of this customization within the cart/order
    private final int customizationId;

    // Optional traceability to the catalog
    private final Integer sourceMenuItemId;  // MenuItem.id
    private final Integer sourceVariantId;   // MenuItemVariant.id

    // Snapshot data used for price and UI
    private final String name;
    private final double extraPrice;

    /** Minimal constructor (without traceability). Maintains backward compatibility. */
    public ItemCustomization(int customizationId, String name, double extraPrice) {
        this(customizationId, null, null, name, extraPrice);
    }

    /** Complete constructor with traceability. */
    public ItemCustomization(
            int customizationId,
            Integer sourceMenuItemId,
            Integer sourceVariantId,
            String name,
            double extraPrice
    ) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name is required");
        if (extraPrice < 0) throw new IllegalArgumentException("extraPrice must be >= 0");
        this.customizationId = customizationId;
        this.sourceMenuItemId = sourceMenuItemId;
        this.sourceVariantId = sourceVariantId;
        this.name = name;
        this.extraPrice = extraPrice;
    }

    /**
     * Factory: creates the snapshot from a MenuItemVariant.
     * Assigns customizationId = 0; the owner (cart/order) can replace it when persisting.
     */
    public static ItemCustomization fromVariant(MenuItemVariant v) {
        if (v == null) throw new IllegalArgumentException("variant is required");
        return new ItemCustomization(
                0,                        // temporary id
                v.getMenuItemId(),        // traceability
                v.getMenuItemVariantId(), // traceability
                v.getName(),              // snapshot
                v.getExtraPrice()         // snapshot
        );
    }

    public int getCustomizationId() { return customizationId; }
    public Integer getSourceMenuItemId() { return sourceMenuItemId; }
    public Integer getSourceVariantId() { return sourceVariantId; }
    public String getName() { return name; }
    public double getExtraPrice() { return extraPrice; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemCustomization)) return false;
        ItemCustomization that = (ItemCustomization) o;
        return customizationId == that.customizationId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(customizationId);
    }

    @Override
    public String toString() {
        return "ItemCustomization{" +
                "customizationId=" + customizationId +
                ", sourceMenuItemId=" + sourceMenuItemId +
                ", sourceVariantId=" + sourceVariantId +
                ", name='" + name + '\'' +
                ", extraPrice=" + extraPrice +
                '}';
    }
}