package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.model.Stock;
import org.example.repository.InventoryRepository;
import org.example.service.OrderService;

import java.time.LocalDate;
import java.util.Optional;

public class StoreOrderDialogController {

    @FXML private ComboBox<String> stockNameCombo;
    @FXML private TextField qtyField;
    @FXML private Label errLabel;

    private final OrderService orderService = new OrderService();
    private final InventoryRepository stockRepo = InventoryRepository.getInstance();

    private Stage dialogStage;

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    @FXML
    private void initialize() {
        // 재고 목록 이름을 ComboBox에 추가
        stockRepo.findAll().forEach(stock ->
                stockNameCombo.getItems().add(stock.getName())
        );
    }

    @FXML
    private void submit() {

        try {
            String selectedName = stockNameCombo.getValue();
            if (selectedName == null || selectedName.isBlank()) {
                errLabel.setText("재고명을 선택하세요.");
                return;
            }

            Optional<Stock> stockOpt = stockRepo.findByName(selectedName);

            if (stockOpt.isEmpty()) {
                errLabel.setText("선택한 재고를 찾을 수 없습니다.");
                return;
            }

            Stock stock = stockOpt.get();

            int qty = Integer.parseInt(qtyField.getText().trim());
            if (qty <= 0) {
                errLabel.setText("수량은 1 이상이어야 합니다.");
                return;
            }

            orderService.requestOrder(stock.getId(), qty, LocalDate.now());
            dialogStage.close();

        } catch (NumberFormatException e) {
            errLabel.setText("수량은 숫자로 입력해야 합니다.");
        } catch (Exception e) {
            errLabel.setText("입력에 문제가 있습니다.");
        }
    }
}
