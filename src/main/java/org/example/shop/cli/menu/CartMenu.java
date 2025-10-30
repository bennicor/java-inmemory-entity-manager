package org.example.shop.cli.menu;

import org.example.shop.cli.commands.cart.*;
import org.example.shop.cli.core.CommandItem;
import org.example.shop.cli.core.Menu;
import org.example.shop.cli.core.MenuItem;

import java.util.List;

public class CartMenu implements Menu {
    @Override
    public String title() {
        return "Меню: Корзина";
    }

    @Override
    public List<MenuItem> items() {
        return List.of(
                new CommandItem(new AddToCartCommand()),
                new CommandItem(new ShowCartCommand()),
                new CommandItem(new ClearCartCommand()),
                new CommandItem(new RemoveFromCartCommand())
        );
    }
}
