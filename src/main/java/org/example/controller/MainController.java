package com.example.stockmanagementsystem.controller;

import com.example.stockmanagementsystem.service.AuthService;
import com.example.stockmanagementsystem.utils.SceneManager;
import javafx.fxml.FXML;

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
