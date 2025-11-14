package org.example.model;

public abstract class Stock {
    private int id;
    private String name;
    private int quantity;
    private int unitPrice;
    private static int reorderThreshold;

    public Stock(int id, String name, int quantity, int unitPrice) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public void increaseStock(int amount) {
        if (amount > 0) {
            this.quantity += amount;
        }
    }

    public void decreaseStock(int amount) {
        if (amount > 0 && this.quantity >= amount) {
            this.quantity -= amount;
        } else {
            // Not enough stock, could throw an exception
            throw new IllegalArgumentException("Not enough stock to decrease.");
        }
    }

    public boolean isLowStock() {
        return this.quantity < reorderThreshold;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public static int getReorderThreshold() {
        return reorderThreshold;
    }

    public static void setReorderThreshold(int reorderThreshold) {
        Stock.reorderThreshold = reorderThreshold;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
