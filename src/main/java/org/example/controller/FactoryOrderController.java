package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.global.exception.GlobalExceptionHandler;
import org.example.model.OrderItem;
import org.example.model.OrderStatus;
import org.example.service.OrderService;

import java.util.Optional;

public class FactoryOrderController {

    @FXML
    private TableView<OrderItem> table;

    @FXML
    private TableColumn<OrderItem, Integer> idCol;
    @FXML
    private TableColumn<OrderItem, Integer> stockIdCol;
    @FXML
    private TableColumn<OrderItem, Integer> qtyCol;
    @FXML
    private TableColumn<OrderItem, String> orderDateCol;
    @FXML
    private TableColumn<OrderItem, String> deliveryDateCol;
    @FXML
    private TableColumn<OrderItem, String> statusCol;

    private final OrderService orderService = new OrderService();

    @FXML
    private void initialize() {
        idCol.setCellValueFactory(v -> v.getValue().orderIdProperty().asObject());
        stockIdCol.setCellValueFactory(v -> v.getValue().stockIdProperty().asObject());
        qtyCol.setCellValueFactory(v -> v.getValue().quantityProperty().asObject());
        orderDateCol.setCellValueFactory(v -> v.getValue().orderDateStringProperty());
        deliveryDateCol.setCellValueFactory(v -> v.getValue().deliveryDateStringProperty());
        statusCol.setCellValueFactory(v -> v.getValue().statusStringProperty());

        refresh();
    }

    @FXML
    private void refresh() {
        try {
            table.getItems().setAll(orderService.getAll());
        } catch (Exception e) {
            GlobalExceptionHandler.getInstance().handle(e);
        }
    }

    @FXML
    private void prepare() {
        changeState("재고 준비중으로 변경하시겠습니까?", OrderStatus.PREPARING);
    }

    @FXML
    private void deliver() {
        changeState("배송중으로 변경하시겠습니까?", OrderStatus.DELIVERING);
    }

    @FXML
    private void complete() {
        changeState("배송 완료 처리하시겠습니까?", OrderStatus.DELIVERED);
    }

    @FXML
    private void cancel() {
        changeState("정말 취소하시겠습니까?", OrderStatus.CANCELLED);
    }

    private void changeState(String msg, OrderStatus status) {
        try {
            OrderItem order = table.getSelectionModel().getSelectedItem();
            if (order == null) return;

            Optional<ButtonType> result =
                    new Alert(Alert.AlertType.CONFIRMATION, msg).showAndWait();

            if (result.isEmpty() || result.get() != ButtonType.OK) return;

            orderService.updateStatus(order, status);

            refresh();

        } catch (Exception e) {
            GlobalExceptionHandler.getInstance().handle(e);
        }
    }
}
