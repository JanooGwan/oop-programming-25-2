package org.example.controller;


import javafx.fxml.FXML;
import org.example.service.AuthService;
import org.example.utils.SceneManager;

import java.io.IOException;

public class MainController {

    private final AuthService authService = new AuthService();

    @FXML
    private void logout() {
        authService.logout();
        try {
            SceneManager.switchTo("LoginView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
