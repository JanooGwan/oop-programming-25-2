package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.global.exception.GlobalExceptionHandler;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.service.AuthService;
import org.example.utils.SceneManager;

public class AuthController {

    @FXML private TextField userIdField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private final AuthService authService = new AuthService();

    @FXML
    private void login() {
        try {
            String id = userIdField.getText();
            String pw = passwordField.getText();

            User user = authService.login(id, pw);

            if (user == null) {
                messageLabel.setText("아이디 또는 비밀번호가 올바르지 않습니다.");
                return;
            }

            switch (user.getRole()) {
                case STORE -> SceneManager.switchTo("InventoryView.fxml");
                case FACTORY -> SceneManager.switchTo("FactoryOrderView.fxml");
                case ADMIN -> SceneManager.switchTo("AdminMainView.fxml");
            }


        } catch (Exception e) {
            GlobalExceptionHandler.getInstance().handle(e);
        }
    }
}
