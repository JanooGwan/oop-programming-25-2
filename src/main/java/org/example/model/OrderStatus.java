package org.example.model;

public enum OrderStatus {
    REQUESTED("신청 완료"),
    PREPARING("재고 준비중"),
    DELIVERING("배송중"),
    DELIVERED("배송 완료"),
    CANCELLED("취소됨");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
