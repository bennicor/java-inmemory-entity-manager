package org.example.shop.cli.menu;

import org.example.shop.cli.commands.product.CreateProductCommand;
import org.example.shop.cli.commands.product.GetProductCommand;
import org.example.shop.cli.commands.product.ListProductsCommand;
import org.example.shop.cli.core.CommandItem;
import org.example.shop.cli.core.Menu;
import org.example.shop.cli.core.MenuItem;

import java.util.List;

public class ProductMenu implements Menu {
    @Override
    public String title() {
        return "Меню: Товары";
    }

    @Override
    public List<MenuItem> items() {
        return List.of(
                new CommandItem(new CreateProductCommand()),
                new CommandItem(new ListProductsCommand()),
                new CommandItem(new GetProductCommand())
        );
    }
}
