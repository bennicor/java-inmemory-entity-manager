package org.example.shop.cli.commands.order;

import org.example.shop.cli.core.Command;
import org.example.shop.cli.core.Context;
import org.example.shop.cli.view.OrderPrinter;
import org.example.shop.domain.model.Order;

public class ShowOrderCommand implements Command {
    @Override
    public String name() {
        return "show-order";
    }

    @Override
    public String description() {
        return "Выводит заказ клиента";
    }

    @Override
    public String usage() {
        return "show-order <customerID>";
    }

    @Override
    public void execute(String[] args, Context ctx) {
        if (args.length < 1) {
            System.out.println("Недостаточно аргументов.");
            return;
        }

        Order o = ctx.orders.get(Integer.parseInt(args[1]));
        OrderPrinter.print(o);
    }
}
