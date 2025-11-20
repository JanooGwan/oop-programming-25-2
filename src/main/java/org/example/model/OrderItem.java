package org.example.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class OrderItem {

    private final IntegerProperty orderId = new SimpleIntegerProperty();
    private final IntegerProperty stockId = new SimpleIntegerProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();

    private final ObjectProperty<LocalDate> orderDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> deliveryDate = new SimpleObjectProperty<>();

    private final ObjectProperty<OrderStatus> status = new SimpleObjectProperty<>();
    private final StringProperty cancelReason = new SimpleStringProperty();

    // ─────────────────────────────
    // ① 풀 파라미터 생성자 (Repo에서 복원할 때 등)
    public OrderItem(int orderId,
                     int stockId,
                     int quantity,
                     LocalDate orderDate,
                     LocalDate deliveryDate,
                     OrderStatus status) {
        this.orderId.set(orderId);
        this.stockId.set(stockId);
        this.quantity.set(quantity);
        this.orderDate.set(orderDate);
        this.deliveryDate.set(deliveryDate);
        this.status.set(status);
    }

    // ② 새 주문 생성용 생성자 (OrderService.requestOrder 에서 사용)
    public OrderItem(int orderId,
                     int stockId,
                     int quantity,
                     LocalDate orderDate) {
        this(orderId, stockId, quantity, orderDate, null, OrderStatus.REQUESTED);
    }

    // ─────────────────────────────
    // orderId
    public int getOrderId() { return orderId.get(); }
    public IntegerProperty orderIdProperty() { return orderId; }

    // stockId
    public int getStockId() { return stockId.get(); }
    public IntegerProperty stockIdProperty() { return stockId; }

    // quantity
    public int getQuantity() { return quantity.get(); }
    public IntegerProperty quantityProperty() { return quantity; }

    // ─────────────────────────────
    // orderDate
    public LocalDate getOrderDate() { return orderDate.get(); }
    public void setOrderDate(LocalDate orderDate) { this.orderDate.set(orderDate); }
    public ObjectProperty<LocalDate> orderDateProperty() { return orderDate; }

    public StringProperty orderDateStringProperty() {
        String val = (getOrderDate() == null) ? "-" : getOrderDate().toString();
        return new SimpleStringProperty(val);
    }

    // ─────────────────────────────
    // deliveryDate
    public LocalDate getDeliveryDate() { return deliveryDate.get(); }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate.set(deliveryDate); }
    public ObjectProperty<LocalDate> deliveryDateProperty() { return deliveryDate; }

    public StringProperty deliveryDateStringProperty() {
        String val = (getDeliveryDate() == null) ? "-" : getDeliveryDate().toString();
        return new SimpleStringProperty(val);
    }

    // ─────────────────────────────
    // status
    public OrderStatus getStatus() { return status.get(); }
    public void setStatus(OrderStatus status) { this.status.set(status); }
    public ObjectProperty<OrderStatus> statusProperty() { return status; }

    public StringProperty statusStringProperty() {
        OrderStatus s = getStatus();

        // 취소 상태라면 사유 포함해서 표시
        if (s == OrderStatus.CANCELLED) {
            String reason = (getCancelReason() == null || getCancelReason().isBlank())
                    ? "(사유 없음)"
                    : "(사유: " + getCancelReason() + ")";
            return new SimpleStringProperty("취소됨 " + reason);
        }

        // 나머지는 기본 라벨 출력
        String label = (s == null) ? "-" : s.getLabel();
        return new SimpleStringProperty(label);
    }


    // ─────────────────────────────
    // cancelReason
    public String getCancelReason() { return cancelReason.get(); }
    public void setCancelReason(String reason) { this.cancelReason.set(reason); }
    public StringProperty cancelReasonProperty() { return cancelReason; }

    // ─────────────────────────────
    // 상태 변경 편의 메서드 (커맨드 패턴에서 사용하기 좋게)
    public void markPreparing() {
        setStatus(OrderStatus.PREPARING);
    }

    public void markDelivering() {
        setStatus(OrderStatus.DELIVERING);
    }

    public void markDelivered() {
        setStatus(OrderStatus.DELIVERED);
        setDeliveryDate(LocalDate.now());
    }

    public void cancel(String reason) {
        setStatus(OrderStatus.CANCELLED);
        setCancelReason(reason);
    }
}
