package org.example.shop.cli.commands.product;

import org.example.shop.cli.core.Command;
import org.example.shop.cli.core.Context;
import org.example.shop.domain.model.Product;

public class GetProductCommand implements Command {
    @Override
    public String name() {
        return "get-product";
    }

    @Override
    public String description() {
        return "Найти товар по ID";
    }

    @Override
    public String usage() {
        return "get-product <id>";
    }

    @Override
    public void execute(String[] args, Context ctx) {
        if (args.length < 1) {
            System.out.println("Недостаточно аргументов.");
            return;
        }

        Product p = ctx.products.get(Integer.parseInt(args[1]));
        System.out.println("Найден товар id=" + p.getId() + " | " + p.getCode() + " | " + p.getName());
    }
}
