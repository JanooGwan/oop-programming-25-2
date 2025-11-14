package org.example.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class IngredientStock extends Stock implements Perishable {
    private LocalDate expiryDate;

    public IngredientStock(int id, String name, int quantity, int unitPrice, LocalDate expiryDate) {
        super(id, name, quantity, unitPrice);
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public long daysUntilExpiry() {
        return ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
    }
    
    @Override
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}
