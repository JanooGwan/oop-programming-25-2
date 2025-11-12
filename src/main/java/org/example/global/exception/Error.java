package org.example.global.exception;

public enum Error {
    OUT_OF_STOCK("재고가 부족합니다.");

    private String message;

    Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
