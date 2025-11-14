package org.example.model;

public enum OrderStatus {
    REQUESTED,      // 발주 신청
    PREPARING,      // 배송 준비중
    DELIVERING,     // 배송중
    DELIVERED,      // 배송 완료
    CANCELLED       // 발주 취소
}
