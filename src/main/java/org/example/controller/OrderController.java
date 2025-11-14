package org.example.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.OrderItem;
import org.example.model.OrderStatus;
import org.example.service.OrderService;

import java.time.LocalDate;
import java.util.Optional;

public class OrderController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<OrderItem> orderTable;
    @FXML
    private TableColumn<OrderItem, Integer> orderIdColumn;
    @FXML
    private TableColumn<OrderItem, Integer> stockIdColumn;
    @FXML
    private TableColumn<OrderItem, Integer> quantityColumn;
    @FXML
    private TableColumn<OrderItem, LocalDate> orderDateColumn;
    @FXML
    private TableColumn<OrderItem, LocalDate> deliveryDateColumn;
    @FXML
    private TableColumn<OrderItem, OrderStatus> statusColumn;

    private final OrderService orderService = new OrderService();
    private final ObservableList<OrderItem> orderList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        stockIdColumn.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        deliveryDateColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadOrderData();
    }

    private void loadOrderData() {
        orderList.setAll(orderService.getAllOrders());
        orderTable.setItems(orderList);
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();
        orderList.setAll(orderService.searchByKeyword(keyword));
        orderTable.setItems(orderList);
    }

    @FXML
    private void handleRefresh() {
        searchField.clear();
        loadOrderData();
    }

    @FXML
    private void handlePrepare() {
        OrderItem selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            orderService.prepareDelivery(selectedOrder.getOrderId());
            handleRefresh();
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an order to prepare.");
        }
    }

    @FXML
    private void handleDeliver() {
        OrderItem selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            orderService.startDelivery(selectedOrder.getOrderId());
            handleRefresh();
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an order to deliver.");
        }
    }

    @FXML
    private void handleComplete() {
        OrderItem selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            orderService.completeOrder(selectedOrder.getOrderId());
            handleRefresh();
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an order to complete.");
        }
    }

    @FXML
    private void handleCancel() {
        OrderItem selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            Optional<ButtonType> result = showConfirmation("Are you sure you want to cancel this order?");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                orderService.cancelOrder(selectedOrder.getOrderId(), "Cancelled by user");
                handleRefresh();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an order to cancel.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Optional<ButtonType> showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }
}
