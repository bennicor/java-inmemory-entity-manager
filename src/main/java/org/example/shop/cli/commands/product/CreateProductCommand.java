package org.example.shop.cli.commands.product;

import org.example.shop.cli.core.Command;
import org.example.shop.cli.core.Context;
import org.example.shop.domain.model.Product;

public class CreateProductCommand implements Command {
    @Override
    public String name() {
        return "create-product";
    }

    @Override
    public String description() {
        return "Создать товар";
    }

    @Override
    public String usage() {
        return "create-product <code> <name> <price>";
    }

    @Override
    public void execute(String[] args, Context ctx) {
        if (args.length < 3) {
            System.out.println("Недостаточно аргументов.");
            return;
        }

         Product p = ctx.products.create(args[1], args[2], Float.parseFloat(args[3]),
                0.f, 0, 0, 0, "");
        System.out.println("Создан товар id=" + p.getId() + " | " + p.getCode() + " | " + p.getName());
    }
}
