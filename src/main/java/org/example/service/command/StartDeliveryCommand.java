package org.example.service.command;

import org.example.model.OrderItem;

public class StartDeliveryCommand implements OrderCommand {
    @Override
    public void execute(OrderItem order) {
        order.markDelivering();
    }
}
