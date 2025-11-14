package org.example.model;

import java.time.LocalDate;

public class OrderItem {
    private int orderId;
    private int stockId;
    private int quantity;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private OrderStatus status;
    private String cancelReason;

    public OrderItem(int orderId, int stockId, int quantity, LocalDate orderDate) {
        this.orderId = orderId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.status = OrderStatus.REQUESTED;
    }

    public void setStatusPreparing() {
        this.status = OrderStatus.PREPARING;
    }

    public void setStatusDelivering() {
        this.status = OrderStatus.DELIVERING;
    }

    public void setStatusDelivered() {
        this.status = OrderStatus.DELIVERED;
        this.deliveryDate = LocalDate.now();
    }

    public void cancel(String reason) {
        this.status = OrderStatus.CANCELLED;
        this.cancelReason = reason;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public int getStockId() {
        return stockId;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getCancelReason() {
        return cancelReason;
    }
}
