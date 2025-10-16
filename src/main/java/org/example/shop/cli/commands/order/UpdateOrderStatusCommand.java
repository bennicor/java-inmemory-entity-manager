package org.example.shop.cli.commands.order;

import org.example.shop.cli.core.Command;
import org.example.shop.cli.core.Context;

public class UpdateOrderStatusCommand implements Command {
    @Override
    public String name() {
        return "update-order-status";
    }

    @Override
    public String description() {
        return "Обновить статус заказа";
    }

    @Override
    public String usage() {
        return "update-order-status <customerID> <status>";
    }

    @Override
    public void execute(String[] args, Context ctx) {
        if (args.length < 2) {
            System.out.println("Недостаточно аргументов.");
            return;
        }

        ctx.orders.updateStatus(Integer.parseInt(args[1]), args[2]);
        System.out.println("Статус заказа обновлен");
    }
}
