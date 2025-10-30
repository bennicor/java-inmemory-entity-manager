package org.example.shop.cli.commands.product;

import org.example.shop.cli.core.Command;
import org.example.shop.cli.core.Context;
import org.example.shop.domain.model.Product;

import java.util.List;

public class ListProductsCommand implements Command {
    @Override
    public String name() {
        return "list-products";
    }

    @Override
    public String description() {
        return "Вывести список товаров";
    }

    @Override
    public String usage() {
        return "list-products";
    }

    @Override
    public void execute(String[] args, Context ctx) {
        List<Product> list = ctx.products.list();
        if (list.isEmpty()) {
            System.out.println("(нет товаров)");
            return;
        }

        for (Product p : list) {
            System.out.printf("- id=%d code=%s name=%s price=%s%n",
                    p.getId(), p.getCode(), p.getName(), p.getPrice());
        }
    }
}
