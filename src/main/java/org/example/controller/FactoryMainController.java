package org.example.controller;

import javafx.fxml.FXML;
import org.example.service.AuthService;
import org.example.utils.SceneManager;

public class FactoryMainController {

    private final AuthService authService = new AuthService();

    @FXML
    private void openOrders() {
        SceneManager.switchTo("FactoryOrderView.fxml");
    }

    @FXML
    private void logout() {
        authService.logout();
        SceneManager.switchTo("LoginView.fxml");
    }
}
