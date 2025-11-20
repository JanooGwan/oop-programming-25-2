package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.global.exception.AppException;
import org.example.global.exception.ErrorCode;
import org.example.global.exception.GlobalExceptionHandler;
import org.example.service.InventoryService;

import java.time.LocalDate;

public class AddStockDialogController {

    @FXML private TextField nameField;
    @FXML private TextField quantityField;
    @FXML private TextField unitPriceField;
    @FXML private CheckBox isIngredientCheck;
    @FXML private DatePicker expiryDatePicker;
    @FXML private Label errorMessage;

    private Stage dialogStage;
    private final InventoryService inventoryService = InventoryService.getInstance();

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    @FXML
    public void initialize() {
        // 식재료가 아니면 유통기한입력 비활성화
        expiryDatePicker.disableProperty()
                .bind(isIngredientCheck.selectedProperty().not());
    }

    @FXML
    private void handleConfirm() {
        try {
            String name = nameField.getText();
            int qty = Integer.parseInt(quantityField.getText());
            int price = Integer.parseInt(unitPriceField.getText());

            boolean isIng = isIngredientCheck.isSelected();
            LocalDate expiry = expiryDatePicker.getValue();

            if (name.isEmpty()) throw new AppException(ErrorCode.INVALID_INPUT);
            if (qty < 0 || price < 0) throw new AppException(ErrorCode.NEGATIVE_VALUE);

            if (isIng && expiry == null) {
                errorMessage.setText("식재료는 유통기한을 반드시 입력해야 합니다.");
                return;
            }

            inventoryService.createStock(name, qty, price, isIng ? expiry : null);

            dialogStage.close();

        } catch (NumberFormatException e) {
            errorMessage.setText("수량과 가격은 숫자만 입력할 수 있습니다.");
        } catch (Exception e) {
            GlobalExceptionHandler.getInstance().handle(e);
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
