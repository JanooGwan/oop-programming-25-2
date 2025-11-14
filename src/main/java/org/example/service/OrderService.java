package org.example.service;

import org.example.model.OrderItem;
import org.example.model.Stock;
import org.example.repository.InventoryRepository;
import org.example.repository.OrderRepository;

import java.time.LocalDate;
import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository = OrderRepository.getInstance();
    private final InventoryRepository inventoryRepository = InventoryRepository.getInstance();
    private final NotificationService notificationService = new NotificationService();

    public OrderItem requestOrder(int stockId, int quantity) {
        int orderId = orderRepository.getNextId();
        OrderItem newOrder = new OrderItem(orderId, stockId, quantity, LocalDate.now());
        orderRepository.save(newOrder);
        return newOrder;
    }

    public void autoReorderForLowStock() {
        List<Stock> allStock = inventoryRepository.findAll();
        for (Stock stock : allStock) {
            if (stock.isLowStock()) {
                notificationService.sendLowStockAlert(stock);
                // Automatically reorder a default quantity (e.g., 50)
                requestOrder(stock.getId(), 50);
            }
        }
    }

    public void cancelOrder(int orderId, String reason) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.cancel(reason);
            orderRepository.save(order);
        });
    }

    public void completeOrder(int orderId) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatusDelivered();
            orderRepository.save(order);
            // Add the ordered quantity to the stock
            inventoryRepository.findById(order.getStockId()).ifPresent(stock -> {
                stock.increaseStock(order.getQuantity());
                inventoryRepository.save(stock);
            });
        });
    }

    public void prepareDelivery(int orderId) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatusPreparing();
            orderRepository.save(order);
        });
    }

    public void startDelivery(int orderId) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatusDelivering();
            orderRepository.save(order);
        });
    }

    public List<OrderItem> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderItem> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllOrders();
        }
        return orderRepository.searchByKeyword(keyword);
    }
}
