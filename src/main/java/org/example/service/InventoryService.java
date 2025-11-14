package org.example.service;


import org.example.model.IngredientStock;
import org.example.model.NonIngredientStock;
import org.example.model.Stock;
import org.example.repository.InventoryRepository;

import java.time.LocalDate;
import java.util.List;

public class InventoryService {
    private final InventoryRepository inventoryRepository = InventoryRepository.getInstance();
    private final NotificationService notificationService = new NotificationService();

    public void createStock(String name, int quantity, int unitPrice, LocalDate expiryDate) {
        int newId = inventoryRepository.getNextId();
        Stock newStock;
        if (expiryDate != null) {
            newStock = new IngredientStock(newId, name, quantity, unitPrice, expiryDate);
        } else {
            newStock = new NonIngredientStock(newId, name, quantity, unitPrice);
        }
        inventoryRepository.save(newStock);
    }

    public void manualAdd(int stockId, int quantity) {
        inventoryRepository.findById(stockId).ifPresent(stock -> {
            stock.increaseStock(quantity);
            inventoryRepository.save(stock);
        });
    }

    public void auditExpiryAndNotify() {
        List<Stock> allStock = inventoryRepository.findAll();
        for (Stock stock : allStock) {
            if (stock instanceof IngredientStock) {
                IngredientStock ingredient = (IngredientStock) stock;
                // Notify if expired or expiring within 7 days
                if (ingredient.isExpired() || ingredient.daysUntilExpiry() <= 7) {
                    notificationService.sendExpiryAlert(ingredient);
                }
            }
        }
    }
    
    public Stock getAllStock() {
        return (Stock) inventoryRepository.findAll();
    }

    public void checkLowStockAndNotify() {
        List<Stock> allStock = inventoryRepository.findAll();
        for (Stock stock : allStock) {
            if (stock.isLowStock()) {
                notificationService.sendLowStockAlert(stock);
            }
        }
    }
}
