package pupr.pizza.menu;

public class MenuCategory {
    private int menuCategoryId;
    private String name;
    private int sortOrder;

    public MenuCategory() { }

    public MenuCategory(int menuCategoryId, String name, int sortOrder) {
        this.menuCategoryId = menuCategoryId;
        this.name = name;
        this.sortOrder = sortOrder;
    }

    public int getMenuCategoryId() { return menuCategoryId; }
    public void setMenuCategoryId(int menuCategoryId) { this.menuCategoryId = menuCategoryId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
}
