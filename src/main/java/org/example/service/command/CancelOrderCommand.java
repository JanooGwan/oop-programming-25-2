package org.example.service.command;

import org.example.model.OrderItem;

public class CancelOrderCommand implements OrderCommand {

    private final String reason;

    public CancelOrderCommand(String reason) {
        this.reason = reason;
    }

    @Override
    public void execute(OrderItem order) {
        order.cancel(reason);
    }
}
