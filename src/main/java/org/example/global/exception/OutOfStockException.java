package org.example.global.exception;

public class OutOfStockException extends RuntimeException {
    private Error error;

    public OutOfStockException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
}
