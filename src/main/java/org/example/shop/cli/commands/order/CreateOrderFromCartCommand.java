package org.example.shop.cli.commands.order;

import org.example.shop.cli.core.Command;
import org.example.shop.cli.core.Context;
import org.example.shop.cli.view.OrderPrinter;
import org.example.shop.domain.model.Order;

public class CreateOrderFromCartCommand implements Command {
    @Override
    public String name() {
        return "create-order-from-cart";
    }

    @Override
    public String description() {
        return "Формирует заказ по корзине клиента";
    }

    @Override
    public String usage() {
        return "create-order-from-cart <customerID> <deliveryCost> <paymentMethod>";
    }

    @Override
    public void execute(String[] args, Context ctx) {
        if (args.length < 3) {
            System.out.println("Недостаточно аргументов.");
            return;
        }

        Order o = ctx.orders.createOrderFromCart(
                Integer.parseInt(args[1]), Float.parseFloat(args[2]), args[3]);
        OrderPrinter.print(o);
    }
}
