package org.example.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.model.OrderItem;
import org.example.model.Stock;
import org.example.service.InventoryService;
import org.example.service.OrderService;
import org.example.utils.SceneManager;

public class StoreOrderController {

    @FXML private TableView<OrderItem> orderTable;

    @FXML private TableColumn<OrderItem, Integer> orderIdCol;
    @FXML private TableColumn<OrderItem, String> stockNameCol;
    @FXML private TableColumn<OrderItem, Integer> qtyCol;
    @FXML private TableColumn<OrderItem, String> orderDateCol;
    @FXML private TableColumn<OrderItem, String> statusCol;

    private final OrderService orderService = new OrderService();
    private final InventoryService inventoryService = InventoryService.getInstance();

    private final ObservableList<OrderItem> orders = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        orderIdCol.setCellValueFactory(v -> v.getValue().orderIdProperty().asObject());

        stockNameCol.setCellValueFactory(v -> {
            int stockId = v.getValue().getStockId();
            Stock found = inventoryService.getById(stockId);
            return new SimpleStringProperty(found != null ? found.getName() : "(삭제됨)");
        });

        qtyCol.setCellValueFactory(v -> v.getValue().quantityProperty().asObject());
        orderDateCol.setCellValueFactory(v -> v.getValue().orderDateStringProperty());
        statusCol.setCellValueFactory(v -> v.getValue().statusStringProperty());

        // ★ 오른쪽 여백 제거
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        refresh();

        orderTable.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) ->
                        cancelButton.setDisable(newV == null));
    }


    @FXML private Button cancelButton;

    private void refresh() {
        orders.setAll(orderService.getAll());
        orderTable.setItems(orders);
    }

    @FXML
    private void goInventory() {
        SceneManager.switchTo("InventoryView.fxml");
    }

    @FXML
    private void logout() {
        SceneManager.switchTo("LoginView.fxml");
    }

    @FXML
    private void openOrderDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/stockmanagementsystem/StoreOrderDialog.fxml")
            );
            VBox root = loader.load();

            Stage dlg = new Stage();
            dlg.initModality(Modality.APPLICATION_MODAL);
            dlg.initOwner(orderTable.getScene().getWindow());
            dlg.setTitle("발주 신청");

            StoreOrderDialogController controller = loader.getController();
            controller.setDialogStage(dlg);

            dlg.setScene(new Scene(root));
            dlg.showAndWait();

            refresh();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelOrder() {
        OrderItem item = orderTable.getSelectionModel().getSelectedItem();
        if (item == null) return;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("신청 취소");
        dialog.setHeaderText("취소 사유를 입력하세요:");
        dialog.setContentText("사유:");

        var result = dialog.showAndWait();
        result.ifPresent(reason -> {
            orderService.cancel(item, reason);
            refresh();
        });
    }
}
