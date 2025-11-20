package org.example.service;

import org.example.model.User;
import org.example.model.UserRole;
import org.example.repository.UserRepository;
import org.example.utils.PasswordHasher;
import org.example.utils.SessionManager;

public class AuthService {

    private final UserRepository repo = UserRepository.getInstance();
    private final SessionManager session = SessionManager.getInstance();

    public AuthService() {
        preloadUsers();
    }

    /** 초기 계정 2개 생성 */
    private void preloadUsers() {
        if (repo.findAll().isEmpty()) {
            repo.save(new User("store", PasswordHasher.sha256("store"), "음식점 계정", UserRole.STORE));
            repo.save(new User("factory", PasswordHasher.sha256("factory"), "공장 계정", UserRole.FACTORY));
        }
    }

    /** 로그인 */
    public User login(String id, String pw) {
        return repo.findById(id)
                .filter(u -> u.getPasswordHash().equals(PasswordHasher.sha256(pw)))
                .map(u -> {
                    session.login(u);
                    return u;
                })
                .orElse(null);
    }

    public void logout() {
        session.logout();
    }
}
