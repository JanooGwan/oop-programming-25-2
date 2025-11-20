package org.example.global.exception;

public enum ErrorCode {

    // 재고 관련
    NOT_ENOUGH_STOCK("재고가 부족합니다."),
    INVALID_INPUT("올바르지 않은 입력입니다."),
    STOCK_NOT_FOUND("해당 재고를 찾을 수 없습니다."),
    NEGATIVE_VALUE("수량 또는 가격은 음수가 될 수 없습니다."),

    // 발주 관련
    ORDER_NOT_FOUND("해당 주문을 찾을 수 없습니다."),
    INVALID_ORDER_STATE("해당 상태로 변경할 수 없습니다."),

    // 인증 관련
    USER_NOT_FOUND("존재하지 않는 사용자입니다."),
    WRONG_PASSWORD("비밀번호가 올바르지 않습니다."),
    LOGIN_REQUIRED("로그인이 필요합니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
