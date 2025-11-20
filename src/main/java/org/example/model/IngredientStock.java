package org.example.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class IngredientStock extends Stock {

    private final ObjectProperty<LocalDate> expiryDate = new SimpleObjectProperty<>();

    public IngredientStock(int id, String name, int qty, int price, int threshold, LocalDate expiry) {
        super(id, name, qty, price, threshold);
        this.expiryDate.set(expiry);
    }

    public LocalDate getExpiryDate() {
        return expiryDate.get();
    }

    public ObjectProperty<LocalDate> expiryDateProperty() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiry) {
        this.expiryDate.set(expiry);
    }

    /** 남은 일수 계산 */
    public long daysUntilExpiry() {
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), getExpiryDate());
    }
}
