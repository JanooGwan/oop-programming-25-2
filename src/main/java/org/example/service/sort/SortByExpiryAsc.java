package org.example.service.sort;

import org.example.model.IngredientStock;
import org.example.model.Stock;
import java.util.Comparator;
import java.util.List;

public class SortByExpiryAsc implements SortStrategy {
    @Override
    public List<Stock> sort(List<Stock> list) {
        return list.stream()
                .filter(s -> s instanceof IngredientStock)
                .sorted(Comparator.comparing(s -> ((IngredientStock) s).getExpiryDate()))
                .toList();
    }
}
