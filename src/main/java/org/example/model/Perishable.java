package org.example.model;

import java.time.LocalDate;

public interface Perishable {
    boolean isExpired();
    long daysUntilExpiry();
    LocalDate getExpiryDate();
}
