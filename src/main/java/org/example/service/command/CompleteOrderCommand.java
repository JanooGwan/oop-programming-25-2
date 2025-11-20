package org.example.service.command;

import org.example.model.OrderItem;

public class CompleteOrderCommand implements OrderCommand {
    @Override
    public void execute(OrderItem order) {
        order.markDelivered();
    }
}
