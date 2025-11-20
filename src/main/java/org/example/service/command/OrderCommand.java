package org.example.service.command;

import org.example.model.OrderItem;

public interface OrderCommand {
    void execute(OrderItem order);
}
