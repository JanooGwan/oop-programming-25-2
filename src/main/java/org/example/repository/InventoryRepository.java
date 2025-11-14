package org.example.repository;

import com.example.stockmanagementsystem.domain.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class InventoryRepository {
    private static InventoryRepository instance;
    private final List<Stock> items = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    private InventoryRepository() {}

    public static InventoryRepository getInstance() {
        if (instance == null) {
            instance = new InventoryRepository();
        }
        return instance;
    }

    public Stock save(Stock stock) {
        if (stock.getId() == 0) {
            // This is a simplified ID generation.
            int newId = idCounter.incrementAndGet();
            // In a real scenario, you would create a new object with the new ID.
            // For this example, we assume we can create it with a given ID.
            // This part of the logic might need adjustment based on how Stock objects are created.
            // Let's assume Stock has a setId method for this, or constructor allows it.
            // The provided Stock class does not have setId, so we'll need to be creative.
            // The best approach is to create a new object, but let's stick to the plan.
            // The current Stock constructor takes an ID, so we'll manage it from the service.
        }
        // Remove if exists, then add.
        items.removeIf(s -> s.getId() == stock.getId());
        items.add(stock);
        return stock;
    }

    public Optional<Stock> findById(int stockId) {
        return items.stream()
                .filter(item -> item.getId() == stockId)
                .findFirst();
    }

    public List<Stock> findAll() {
        return new ArrayList<>(items);
    }

    public void delete(int stockId) {
        items.removeIf(item -> item.getId() == stockId);
    }
    
    public int getNextId() {
        return idCounter.incrementAndGet();
    }
}
