package pupr.pizza.model;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class User {
    protected Long id;
    protected String name;
    protected String email;
    protected String username;
    protected String password;
    protected LocalDateTime lastSession;
    protected LocalDateTime createdAt;

    public User() {}

    public User(Long id, String name, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getLastSession() { return lastSession; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean authenticate(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            this.lastSession = LocalDateTime.now();
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}