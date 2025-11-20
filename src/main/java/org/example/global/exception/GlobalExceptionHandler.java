package org.example.global.exception;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class GlobalExceptionHandler {

    private static GlobalExceptionHandler instance;

    private GlobalExceptionHandler() {}

    public static GlobalExceptionHandler getInstance() {
        if (instance == null) {
            instance = new GlobalExceptionHandler();
        }
        return instance;
    }

    public void handle(Exception e) {
        e.printStackTrace();
        Platform.runLater(() -> {
            String msg = (e instanceof AppException)
                    ? ((AppException) e).getErrorCode().getMessage()
                    : "알 수 없는 오류가 발생했습니다.";

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("오류");
            alert.setHeaderText("오류 발생");
            alert.setContentText(msg);
            alert.showAndWait();
        });
    }
}
