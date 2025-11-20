package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.model.OrderItem;
import org.example.service.OrderService;

public class OrderCancelDialogController {

    @FXML private TextArea reasonArea;

    private OrderItem order;
    private Runnable refreshCallback;

    private final OrderService orderService = new OrderService();

    public void setOrder(OrderItem order) {
        this.order = order;
    }

    public void setRefreshCallback(Runnable cb) {
        this.refreshCallback = cb;
    }

    @FXML
    private void confirm() {
        String reason = reasonArea.getText();
        if (reason.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "취소 사유를 입력하세요").showAndWait();
            return;
        }

        orderService.cancel(order, reason);

        if (refreshCallback != null) refreshCallback.run();

        close();
    }

    @FXML
    private void close() {
        ((Stage) reasonArea.getScene().getWindow()).close();
    }
}
