package org.example.service;

import javafx.scene.control.Alert;
import org.example.model.IngredientStock;
import org.example.model.Stock;

public class NotificationService {

    public void notifyExpiry(IngredientStock stock) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("유통기한 임박");
        alert.setContentText(stock.getName() + " 재고의 유통기한이 임박했습니다.");
        alert.showAndWait();
    }

    public void notifyLowStock(Stock stock) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("재고 부족");
        alert.setContentText(stock.getName() + " 재고가 부족합니다. (현재 수량: " + stock.getQuantity() + ")");
        alert.showAndWait();
    }
}
