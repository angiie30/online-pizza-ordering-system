package pupr.pizza.model;

public class Employee extends User {
    private Role role;
    private boolean isActive;

    public Employee() {}

    public Employee(Long id, String name, String email, String username, String password, Role role, boolean isActive) {
        super(id, name, email, username, password);
        this.role = role;
        this.isActive = isActive;
    }

    public Role getRole() { return role; }
    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() { return isActive; }

    public void setActive(boolean active) { isActive = active; }
}