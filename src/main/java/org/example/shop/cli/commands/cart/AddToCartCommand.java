package org.example.shop.cli.commands.cart;

import org.example.shop.cli.core.Command;
import org.example.shop.cli.core.Context;

public class AddToCartCommand implements Command {
    @Override
    public String name() {
        return "add-to-cart";
    }

    @Override
    public String description() {
        return "Добавить товар в корзину";
    }

    @Override
    public String usage() {
        return "add-to-cart <customerID> <productID> <quantity>";
    }

    @Override
    public void execute(String[] args, Context ctx) {
        if (args.length < 3) {
            System.out.println("Недостаточно аргументов.");
            return;
        }

        ctx.carts.add(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        System.out.printf("В корзину пользователя %s добавлено %s товаров id=%s%n",
                args[1], args[3], args[2]);
    }
}
