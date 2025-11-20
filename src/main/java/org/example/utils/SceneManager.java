package org.example.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    public static Stage stage;

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    // 단순 화면 교체 + 전역 CSS 적용
    public static void switchTo(String fxml) {
        try {
            Parent root = FXMLLoader.load(
                    SceneManager.class.getResource("/org/example/stockmanagementsystem/" + fxml)
            );

            int width = 900;
            int height = 700;

            // 로그인 화면 크기만 별도 지정
            if (fxml.equals("LoginView.fxml")) {
                width = 450;
                height = 350;
            }

            Scene scene = new Scene(root, width, height);

            // ★★★ 글로벌 CSS 적용 부분 추가 ★★★
            scene.getStylesheets().add(
                    SceneManager.class.getResource("/org/example/stockmanagementsystem/global.css")
                            .toExternalForm()
            );

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
