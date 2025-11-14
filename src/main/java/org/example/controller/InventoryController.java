package org.example.controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.model.IngredientStock;
import org.example.model.Stock;
import org.example.service.InventoryService;

import java.io.IOException;
import java.time.LocalDate;

public class InventoryController {

    @FXML
    private TableView<Stock> inventoryTable;
    @FXML
    private TableColumn<Stock, Integer> idColumn;
    @FXML
    private TableColumn<Stock, String> nameColumn;
    @FXML
    private TableColumn<Stock, Integer> quantityColumn;
    @FXML
    private TableColumn<Stock, Integer> unitPriceColumn;
    @FXML
    private TableColumn<Stock, String> expiryDateColumn;

    private final InventoryService inventoryService = new InventoryService();
    private final ObservableList<Stock> stockList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        expiryDateColumn.setCellValueFactory(cellData -> {
            Stock stock = cellData.getValue();
            if (stock instanceof IngredientStock) {
                LocalDate expiryDate = ((IngredientStock) stock).getExpiryDate();
                return new SimpleStringProperty(expiryDate.toString());
            }
            return new SimpleStringProperty("N/A");
        });

        loadStockData();
    }

    private void loadStockData() {
        stockList.setAll(inventoryService.getAllStock());
        inventoryTable.setItems(stockList);
    }

    @FXML
    private void handleAddStock() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stockmanagementsystem/AddStockDialog.fxml"));
            VBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Stock");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(inventoryTable.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddStockDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            // Refresh table after dialog is closed
            handleRefresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAuditExpiry() {
        inventoryService.auditExpiryAndNotify();
        showAlert(Alert.AlertType.INFORMATION, "Audit Complete", "Expiry date audit has been performed. Check console for alerts.");
    }

    @FXML
    private void handleCheckLowStock() {
        inventoryService.checkLowStockAndNotify();
        showAlert(Alert.AlertType.INFORMATION, "Check Complete", "Low stock check has been performed. Check console for alerts.");
    }
    
    @FXML
    private void handleRefresh() {
        loadStockData();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
