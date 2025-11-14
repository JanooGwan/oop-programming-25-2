package org.example.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneManager {
    private static Stage stage;

    public static void setStage(Stage stage) {
        SceneManager.stage = stage;
    }

    public static void switchTo(String fxmlFile) throws IOException {
        URL fxmlUrl = SceneManager.class.getResource("/org/example/stockmanagementsystem/" + fxmlFile);
        if (fxmlUrl == null) {
            throw new IOException("Cannot find FXML file: " + fxmlFile);
        }
        Parent root = FXMLLoader.load(fxmlUrl);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}
