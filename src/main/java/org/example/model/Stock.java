package org.example.model;

import javafx.beans.property.*;

public abstract class Stock {

    protected IntegerProperty id = new SimpleIntegerProperty();
    protected StringProperty name = new SimpleStringProperty();
    protected IntegerProperty quantity = new SimpleIntegerProperty();
    protected IntegerProperty price = new SimpleIntegerProperty();
    protected IntegerProperty threshold = new SimpleIntegerProperty();

    public Stock(int id, String name, int qty, int price, int threshold) {
        this.id.set(id);
        this.name.set(name);
        this.quantity.set(qty);
        this.price.set(price);
        this.threshold.set(threshold);
    }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }

    public int getQuantity() { return quantity.get(); }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }
    public IntegerProperty quantityProperty() { return quantity; }

    public int getUnitPrice() { return price.get(); }
    public IntegerProperty unitPriceProperty() { return price; }

    public int getReorderThreshold() { return threshold.get(); }
    public IntegerProperty reorderThresholdProperty() { return threshold; }

    public void increaseStock(int amount) { this.quantity.set(getQuantity() + amount); }
    public void decreaseStock(int amount) { this.quantity.set(getQuantity() - amount); }

    public boolean isLowStock() { return getQuantity() < getReorderThreshold(); }
}
