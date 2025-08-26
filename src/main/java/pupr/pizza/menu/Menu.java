package pupr.pizza.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Menu {
    private final List<MenuItem> items = new ArrayList<>();
    private final List<MenuCategory> categories = new ArrayList<>();

    // Menu Items ---------------------------------------
    public List<MenuItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(MenuItem item) {
        if (item != null) items.add(item);
    }

    public void removeItem(int id) {
        items.removeIf(i -> i.getMenuItemId() == id);
    }

    // Menu Categories ---------------------------------------
    public List<MenuCategory> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public void addCategory(MenuCategory category) {
        if (category != null) categories.add(category);
    }

    public void removeCategory(int id) {
        categories.removeIf(c -> c.getMenuCategoryId() == id);
    }
}