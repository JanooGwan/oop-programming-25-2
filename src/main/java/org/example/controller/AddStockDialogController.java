package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.service.InventoryService;

import java.time.LocalDate;

public class AddStockDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField unitPriceField;
    @FXML
    private CheckBox isIngredientCheck;
    @FXML
    private DatePicker expiryDatePicker;
    @FXML
    private Label errorMessage;

    private Stage dialogStage;
    private final InventoryService inventoryService = new InventoryService();

    @FXML
    public void initialize() {
        // Disable expiry date picker if it's not an ingredient
        expiryDatePicker.disableProperty().bind(isIngredientCheck.selectedProperty().not());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleConfirm() {
        if (isInputValid()) {
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            int unitPrice = Integer.parseInt(unitPriceField.getText());
            LocalDate expiryDate = isIngredientCheck.isSelected() ? expiryDatePicker.getValue() : null;

            inventoryService.createStock(name, quantity, unitPrice, expiryDate);
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String message = "";
        if (nameField.getText() == null || nameField.getText().isEmpty()) {
            message += "No valid name!\n";
        }
        try {
            Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            message += "No valid quantity (must be an integer)!\n";
        }
        try {
            Integer.parseInt(unitPriceField.getText());
        } catch (NumberFormatException e) {
            message += "No valid unit price (must be an integer)!\n";
        }
        if (isIngredientCheck.isSelected() && expiryDatePicker.getValue() == null) {
            message += "No valid expiry date for ingredient!\n";
        }

        if (message.isEmpty()) {
            return true;
        } else {
            errorMessage.setText(message);
            return false;
        }
    }
}
