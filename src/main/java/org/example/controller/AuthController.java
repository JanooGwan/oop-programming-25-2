package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.service.AuthService;
import org.example.utils.SceneManager;

import java.io.IOException;

public class AuthController {

    @FXML
    private TextField userIdField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private final AuthService authService = new AuthService();

    @FXML
    private void login() {
        String userId = userIdField.getText();
        String password = passwordField.getText();

        if (userId.isEmpty() || password.isEmpty()) {
            messageLabel.setText("User ID and password cannot be empty.");
            return;
        }

        boolean success = authService.login(userId, password);

        if (success) {
            try {
                SceneManager.switchTo("MainView.fxml");
            } catch (IOException e) {
                messageLabel.setText("Error: Could not load main application view.");
                e.printStackTrace();
            }
        } else {
            messageLabel.setText("Invalid user ID or password.");
        }
    }
}
