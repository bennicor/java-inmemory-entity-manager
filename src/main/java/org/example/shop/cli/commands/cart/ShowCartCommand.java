package org.example.shop.cli.commands.cart;

import org.example.shop.cli.core.Command;
import org.example.shop.cli.core.Context;
import org.example.shop.domain.model.Cart;
import org.example.shop.domain.model.CartItem;

public class ShowCartCommand implements Command {
    @Override
    public String name() {
        return "show-cart";
    }

    @Override
    public String description() {
        return "Показать содержимое корзины";
    }

    @Override
    public String usage() {
        return "show-cart <customerID>";
    }

    @Override
    public void execute(String[] args, Context ctx) {
        if (args.length < 1) {
            System.out.println("Недостаточно аргументов.");
            return;
        }

        Cart cart = ctx.carts.view(Integer.parseInt(args[1]));
        if (cart.getItems().isEmpty()) {
            System.out.println("(корзина пуста)");
            return;
        }

        for (CartItem ci : cart.getItems()) {
            System.out.printf("- productID=%d quantity=%d%n", ci.getProductId(), ci.getQuantity());
        }
    }
}