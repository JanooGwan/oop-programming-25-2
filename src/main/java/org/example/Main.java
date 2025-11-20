package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.service.InventoryService;
import org.example.service.OrderService;
import org.example.utils.SceneManager;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // 초기 데이터 삽입
        InventoryService.getInstance().preloadSampleData();
        new OrderService().preloadSampleOrders();

        SceneManager.setStage(stage);
        stage.setTitle("샌드위치 가게 재고 관리 시스템");
        SceneManager.switchTo("LoginView.fxml");
    }


    public static void main(String[] args) {
        launch();
    }
}
