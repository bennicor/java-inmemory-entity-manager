package org.example.shop.cli.commands.cart;

import org.example.shop.cli.core.Command;
import org.example.shop.cli.core.Context;

public class RemoveFromCartCommand implements Command {
    @Override
    public String name() {
        return "remove-from-cart";
    }

    @Override
    public String description() {
        return "Удаляет товар из корзины";
    }

    @Override
    public String usage() {
        return "remove-from-cart <customerID> <productID>";
    }

    @Override
    public void execute(String[] args, Context ctx) {
        if (args.length < 2) {
            System.out.println("Недостаточно аргументов.");
            return;
        }

        ctx.carts.remove(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        System.out.printf("Из корзины пользователя %s удален товар %s%n",
                args[1], args[2]);
    }
}
