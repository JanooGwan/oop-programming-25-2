package org.example.service.sort;

import org.example.model.Stock;
import java.util.Comparator;
import java.util.List;

public class SortByPriceAsc implements SortStrategy {
    @Override
    public List<Stock> sort(List<Stock> list) {
        return list.stream()
                .sorted(Comparator.comparing(Stock::getUnitPrice))
                .toList();
    }
}
