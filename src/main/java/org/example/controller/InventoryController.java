package org.example.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableRow;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.global.exception.GlobalExceptionHandler;
import org.example.model.IngredientStock;
import org.example.model.Stock;
import org.example.service.InventoryService;
import org.example.utils.SceneManager;

import java.io.IOException;
import java.time.LocalDate;

public class InventoryController {

    @FXML private TableView<Stock> inventoryTable;
    @FXML private TableColumn<Stock, Integer> idCol;
    @FXML private TableColumn<Stock, String> nameCol;
    @FXML private TableColumn<Stock, Integer> qtyCol;
    @FXML private TableColumn<Stock, Integer> priceCol;
    @FXML private TableColumn<Stock, String> expiryCol;

    private final InventoryService inventoryService = InventoryService.getInstance();
    private final ObservableList<Stock> items = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        idCol.setCellValueFactory(v -> v.getValue().idProperty().asObject());
        nameCol.setCellValueFactory(v -> v.getValue().nameProperty());
        qtyCol.setCellValueFactory(v -> v.getValue().quantityProperty().asObject());
        priceCol.setCellValueFactory(v -> v.getValue().unitPriceProperty().asObject());

        // 유통기한 표시 - 비식재료는 빈 문자열
        expiryCol.setCellValueFactory(v -> {
            Stock s = v.getValue();
            if (s instanceof IngredientStock ing) {
                LocalDate d = ing.getExpiryDate();
                return new SimpleStringProperty(d != null ? d.toString() : "");
            }
            return new SimpleStringProperty("");
        });

        // 정렬 막기
        idCol.setSortable(false);
        nameCol.setSortable(false);

        // 오른쪽 빈칸 없이 꽉 채우기
        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // 행 더블클릭 → 상세창
        inventoryTable.setRowFactory(tv -> {
            TableRow<Stock> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    openStockDetailDialog(row.getItem());
                }
            });
            return row;
        });

        refreshTable();
    }

    private void refreshTable() {
        items.setAll(inventoryService.getAll());
        inventoryTable.setItems(items);
    }

    private void openStockDetailDialog(Stock stock) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/stockmanagementsystem/StockDetailDialog.fxml")
            );
            VBox page = loader.load();

            Stage dialog = new Stage();
            dialog.setTitle("재고 상세 정보");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(inventoryTable.getScene().getWindow());
            dialog.setScene(new Scene(page));

            StockDetailDialogController controller = loader.getController();
            controller.setDialogStage(dialog);
            controller.setStock(stock);

            dialog.showAndWait();
            refreshTable();

        } catch (IOException e) {
            GlobalExceptionHandler.getInstance().handle(e);
        }
    }

    @FXML
    private void handleAddStock() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/stockmanagementsystem/AddStockDialog.fxml")
            );
            VBox page = loader.load();

            Stage dialog = new Stage();
            dialog.setTitle("재고 추가");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(inventoryTable.getScene().getWindow());
            dialog.setScene(new Scene(page));

            AddStockDialogController controller = loader.getController();
            controller.setDialogStage(dialog);

            dialog.showAndWait();
            refreshTable();

        } catch (IOException e) {
            GlobalExceptionHandler.getInstance().handle(e);
        }
    }

    @FXML
    private void handleAuditExpiry() {
        try {
            inventoryService.auditExpiry();
        } catch (Exception e) {
            GlobalExceptionHandler.getInstance().handle(e);
        }
    }

    @FXML
    private void handleCheckLowStock() {
        try {
            inventoryService.checkLowStock();
        } catch (Exception e) {
            GlobalExceptionHandler.getInstance().handle(e);
        }
    }

    @FXML
    private void openOrderPage() {
        SceneManager.switchTo("StoreOrderView.fxml");
    }

    @FXML
    private void openLogView() {
        SceneManager.switchTo("LogView.fxml");
    }

    @FXML
    private void logout() {
        SceneManager.switchTo("LoginView.fxml");
    }
}
