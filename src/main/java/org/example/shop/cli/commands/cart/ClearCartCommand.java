package org.example.shop.cli.commands.cart;

import org.example.shop.cli.core.Command;
import org.example.shop.cli.core.Context;

public class ClearCartCommand implements Command {
    @Override
    public String name() {
        return "clear-cart";
    }

    @Override
    public String description() {
        return "Очистить корзину";
    }

    @Override
    public String usage() {
        return "clear-cart <customerID>";
    }

    @Override
    public void execute(String[] args, Context ctx) {
        if (args.length < 1) {
            System.out.println("Недостаточно аргументов.");
            return;
        }


        ctx.carts.clear(Integer.parseInt(args[1]));
        System.out.println("Корзина очищена");
    }
}
