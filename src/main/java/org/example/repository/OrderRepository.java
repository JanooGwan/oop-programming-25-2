package org.example.repository;

import com.example.stockmanagementsystem.domain.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class OrderRepository {
    private static OrderRepository instance;
    private final List<OrderItem> orderItems = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    private OrderRepository() {}

    public static OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    public OrderItem save(OrderItem orderItem) {
         // Remove if exists, then add.
        orderItems.removeIf(o -> o.getOrderId() == orderItem.getOrderId());
        orderItems.add(orderItem);
        return orderItem;
    }

    public Optional<OrderItem> findById(int orderId) {
        return orderItems.stream()
                .filter(item -> item.getOrderId() == orderId)
                .findFirst();
    }

    public List<OrderItem> findAll() {
        return new ArrayList<>(orderItems);
    }
    
    public List<OrderItem> searchByKeyword(String keyword) {
        String lowerCaseKeyword = keyword.toLowerCase();
        return orderItems.stream()
                .filter(order -> 
                    String.valueOf(order.getOrderId()).contains(lowerCaseKeyword) ||
                    String.valueOf(order.getStockId()).contains(lowerCaseKeyword) ||
                    order.getStatus().toString().toLowerCase().contains(lowerCaseKeyword)
                )
                .collect(Collectors.toList());
    }

    public int getNextId() {
        return idCounter.incrementAndGet();
    }
}
