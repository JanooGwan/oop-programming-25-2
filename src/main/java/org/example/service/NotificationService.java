package org.example.service;


import org.example.model.IngredientStock;
import org.example.model.Stock;

public class NotificationService {

    public void sendExpiryAlert(IngredientStock stock) {
        // In a real application, this would send an email, SMS, or push notification.
        // For this example, we'll just print to the console.
        System.out.println("ALERT: Stock item '" + stock.getName() + "' (ID: " + stock.getId() + ") is expiring in " + stock.daysUntilExpiry() + " days.");
    }

    public void sendLowStockAlert(Stock stock) {
        // In a real application, this would send an email, SMS, or push notification.
        // For this example, we'll just print to the console.
        System.out.println("ALERT: Stock item '" + stock.getName() + "' (ID: " + stock.getId() + ") is low on stock. Current quantity: " + stock.getQuantity());
    }
}
