package org.example.repository;

import org.example.model.User;

import java.util.*;

public class UserRepository {

    private static UserRepository instance;

    private final Map<String, User> users = new HashMap<>();

    private UserRepository() {}

    public static UserRepository getInstance() {
        if (instance == null) instance = new UserRepository();
        return instance;
    }

    /** 저장 */
    public void save(User user) {
        users.put(user.getUserId(), user);
    }

    /** 사용자 ID로 찾기 */
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    /** 전체 조회 (필요 시) */
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
