package org.example.shop.cli.commands.order;

import org.example.shop.cli.core.Command;
import org.example.shop.cli.core.Context;
import org.example.shop.cli.view.OrderPrinter;
import org.example.shop.domain.model.Order;

import java.util.List;

public class ListOrdersByCustomerCommand implements Command {
    @Override
    public String name() {
        return "list-orders-by-customer";
    }

    @Override
    public String description() {
        return "Выводит все заказы клиента";
    }

    @Override
    public String usage() {
        return "list-orders-by-customer <customerID>";
    }

    @Override
    public void execute(String[] args, Context ctx) {
        if (args.length < 1) {
            System.out.println("Недостаточно аргументов.");
            return;
        }

        List<Order> orders = ctx.orders.listByCustomer(Integer.parseInt(args[1]));
        for (Order o : orders) {
            OrderPrinter.printBrief(o);
        }
    }
}
