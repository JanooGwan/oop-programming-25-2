package org.example.service.sort;

import org.example.model.Stock;
import java.util.List;

public interface SortStrategy {
    List<Stock> sort(List<Stock> list);
}
