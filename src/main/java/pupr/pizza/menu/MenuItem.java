package pupr.pizza.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MenuItem {
    private int menuItemId;
    private String name;
    private String description;
    private double basePrice;
    private String productImageUrl;
    private int menuCategoryId;
    private boolean isActive;

    //  Variants of this MenuItem
    private final List<MenuItemVariant> variants = new ArrayList<>();

    public MenuItem() { }

    public MenuItem(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
        this.isActive = true;
    }

    public MenuItem(int menuItemId, String name, String description,
                    double basePrice, String productImageUrl,
                    int menuCategoryId, boolean isActive) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.productImageUrl = productImageUrl;
        this.menuCategoryId = menuCategoryId;
        this.isActive = isActive;
    }

    public int getMenuItemId() { return menuItemId; }
    public void setMenuItemId(int menuItemId) { this.menuItemId = menuItemId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    public String getProductImageUrl() { return productImageUrl; }
    public void setProductImageUrl(String productImageUrl) { this.productImageUrl = productImageUrl; }

    public int getMenuCategoryId() { return menuCategoryId; }
    public void setMenuCategoryId(int menuCategoryId) { this.menuCategoryId = menuCategoryId; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public double getPrice() { return this.basePrice; }

    // === Menu Item Variants ===
    public List<MenuItemVariant> getVariants() {
        return Collections.unmodifiableList(variants);
    }

    public void addVariant(MenuItemVariant variant) {
        if (variant == null) return;

        variant.setMenuItemId(this.menuItemId);

        for (int i = 0; i < variants.size(); i++) {
            if (variants.get(i).getMenuItemVariantId() == variant.getMenuItemVariantId()) {
                variants.set(i, variant);
                return;
            }
        }
        variants.add(variant);
    }

  
    public boolean removeVariantById(int menuItemVariantId) {
        Iterator<MenuItemVariant> it = variants.iterator();
        while (it.hasNext()) {
            if (it.next().getMenuItemVariantId() == menuItemVariantId) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    // === End Menu Item Variants ===
}

