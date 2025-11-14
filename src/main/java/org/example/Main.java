package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


import javafx.application.Application;
import javafx.stage.Stage;
import org.example.model.SignUpRequest;
import org.example.model.UserRole;
import org.example.service.AuthService;
import org.example.utils.SceneManager;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Add a default admin user for testing
        setupDefaultUser();

        SceneManager.setStage(stage);
        stage.setTitle("Stock Management System");
        SceneManager.switchTo("LoginView.fxml");
    }

    private void setupDefaultUser() {
        AuthService authService = new AuthService();
        // Simple check to avoid creating the user every time
        if (authService.login("admin", "admin") == false) {
            authService.signUp(new SignUpRequest("admin", "admin", "Administrator", UserRole.ADMIN));
        }
        authService.logout(); // Logout after checking/creating
    }

    public static void main(String[] args) {
        launch();
    }
}
