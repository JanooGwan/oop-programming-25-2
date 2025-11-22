package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import org.example.model.LogItem;
import org.example.repository.LogRepository;
import org.example.utils.SceneManager;

public class LogViewController {

    @FXML private TableView<LogItem> logTable;
    @FXML private TableColumn<LogItem, String> timeCol;
    @FXML private TableColumn<LogItem, String> actionCol;

    @FXML
    private void initialize() {
        timeCol.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getTime()));
        actionCol.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getAction()));

        logTable.getItems().setAll(LogRepository.getInstance().findAll());
    }

    @FXML
    private void goBack() {
        SceneManager.switchTo("InventoryView.fxml");
    }
}
