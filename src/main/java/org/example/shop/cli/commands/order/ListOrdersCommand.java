package org.example.shop.cli.commands.order;

import org.example.shop.cli.core.Command;
import org.example.shop.cli.core.Context;
import org.example.shop.cli.view.OrderPrinter;
import org.example.shop.domain.model.Order;

import java.util.List;

public class ListOrdersCommand implements Command {
    @Override
    public String name() {
        return "list-orders";
    }

    @Override
    public String description() {
        return "Выводит все заказы";
    }

    @Override
    public String usage() {
        return "list-orders";
    }

    @Override
    public void execute(String[] args, Context ctx) {
        List<Order> orders = ctx.orders.list();
        if (orders.isEmpty()) {
            System.out.println("(нет заказов)");
            return;
        }

        for (Order o : orders) {
            OrderPrinter.printBrief(o);
        }
    }
}
