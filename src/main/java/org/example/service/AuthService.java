package org.example.service;

import org.example.model.SignUpRequest;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.utils.PasswordHasher;
import org.example.utils.SessionManager;

import java.util.Optional;

public class AuthService {
    private final UserRepository userRepository = UserRepository.getInstance();
    private final SessionManager sessionManager = SessionManager.getInstance();

    public boolean signUp(SignUpRequest request) {
        if (userRepository.findById(request.getUserId()).isPresent()) {
            // User already exists
            return false;
        }
        String passwordHash = PasswordHasher.sha256(request.getPassword());
        User newUser = new User(request.getUserId(), passwordHash, request.getName(), request.getRole());
        userRepository.save(newUser);
        return true;
    }

    public boolean login(String userId, String password) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String passwordHash = PasswordHasher.sha256(password);
            if (user.getPasswordHash().equals(passwordHash)) {
                sessionManager.login(user);
                return true;
            }
        }
        return false;
    }

    public void logout() {
        sessionManager.logout();
    }
}
