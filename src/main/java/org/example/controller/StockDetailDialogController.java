package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.global.exception.GlobalExceptionHandler;
import org.example.model.IngredientStock;
import org.example.model.Stock;
import org.example.repository.LogRepository;
import org.example.service.InventoryService;

import java.time.LocalDate;

public class StockDetailDialogController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField qtyField;
    @FXML private TextField priceField;
    @FXML private TextField expiryField;
    @FXML private Label errorLabel;

    private Stage dialogStage;
    private Stock stock;
    private final InventoryService inventoryService = InventoryService.getInstance();

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    /** InventoryController 에서 선택된 Stock 주입 */
    public void setStock(Stock stock) {
        this.stock = stock;

        idField.setText(String.valueOf(stock.getId()));
        nameField.setText(stock.getName());
        qtyField.setText(String.valueOf(stock.getQuantity()));
        priceField.setText(String.valueOf(stock.getUnitPrice()));

        if (stock instanceof IngredientStock ing) {
            LocalDate d = ing.getExpiryDate();
            expiryField.setText(d != null ? d.toString() : "-");
        } else {
            expiryField.setText("해당 없음");
        }
    }

    /** 수량 저장 버튼 */
    @FXML
    private void handleSaveQuantity() {
        if (stock == null) return;

        try {
            int newQty = Integer.parseInt(qtyField.getText().trim());
            if (newQty < 0) {
                errorLabel.setText("수량은 0 이상이어야 합니다.");
                return;
            }

            int oldQty = stock.getQuantity();
            stock.setQuantity(newQty);

            LogRepository.getInstance().add(
                    "[수량 변경] " + stock.getName() +
                            " | " + oldQty + " → " + newQty);

            dialogStage.close();
        } catch (NumberFormatException e) {
            errorLabel.setText("수량에는 숫자만 입력할 수 있습니다.");
        }
    }

    /** 삭제 버튼 */
    @FXML
    private void handleDelete() {
        if (stock == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("재고 삭제");
        alert.setHeaderText("해당 재고를 삭제하시겠습니까?");
        alert.setContentText("ID: " + stock.getId() + ", 이름: " + stock.getName());

        alert.showAndWait().ifPresent(button -> {
            if (button == ButtonType.OK) {
                try {
                    inventoryService.deleteStock(stock.getId());
                    dialogStage.close();
                } catch (Exception e) {
                    GlobalExceptionHandler.getInstance().handle(e);
                }
            }
        });
    }

    /** 닫기 버튼 */
    @FXML
    private void handleClose() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    @FXML
    private void handleIncreaseQty() {
        try {
            int value = Integer.parseInt(qtyField.getText().trim());
            qtyField.setText(String.valueOf(value + 1));
        } catch (NumberFormatException e) {
            qtyField.setText("0");
        }
    }

    @FXML
    private void handleDecreaseQty() {
        try {
            int value = Integer.parseInt(qtyField.getText().trim());
            if (value > 0) value -= 1;
            qtyField.setText(String.valueOf(value));
        } catch (NumberFormatException e) {
            qtyField.setText("0");
        }
    }

}
