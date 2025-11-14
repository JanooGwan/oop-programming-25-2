package org.example.repository;

import org.example.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static UserRepository instance;
    private final List<User> users = new ArrayList<>();

    private UserRepository() {}

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public void save(User user) {
        // For simplicity, we assume userId is unique.
        // In a real app, you'd check for existing users.
        users.add(user);
    }

    public Optional<User> findById(String userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }
}
