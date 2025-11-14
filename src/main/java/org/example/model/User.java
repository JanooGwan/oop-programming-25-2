package org.example.model;

public class User {
    private String userId;
    private String passwordHash;
    private String name;
    private UserRole role;

    public User(String userId, String passwordHash, String name, UserRole role) {
        this.userId = userId;
        this.passwordHash = passwordHash;
        this.name = name;
        this.role = role;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }
}
