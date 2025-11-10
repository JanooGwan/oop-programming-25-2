package org.example.domain;

import org.example.global.exception.Error;
import org.example.global.exception.OutOfStockException;

public class Stock {
    public static Long stockCount = 0L;

    private Long id;
    private String name;
    private int quantity;

    // private boolean isReordered; (원래 엑셀에는 static 으로 되어 있으나 일단 변경함 static 안하는게 나을듯, 일단 보류)
    private int unitPrice;

    public Stock(String name, int quantity, int unitPrice) {
        this.id = ++stockCount;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public void increaseStock(int amount) {
        quantity += amount;
    }

    public void increaseStock() {
        increaseStock(1);
    }

    public void decreaseStock(int amount) {
        if(quantity - amount < 0) {
            throw new OutOfStockException(Error.OUT_OF_STOCK);
        }
        quantity -= amount;
    }

    public void decreaseStock() {
        decreaseStock(1);
    }
}
