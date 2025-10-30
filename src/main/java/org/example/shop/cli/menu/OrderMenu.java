package org.example.shop.cli.menu;

import org.example.shop.cli.commands.order.*;
import org.example.shop.cli.core.CommandItem;
import org.example.shop.cli.core.Menu;
import org.example.shop.cli.core.MenuItem;

import java.util.List;

public class OrderMenu implements Menu {
    @Override
    public String title() {
        return "Меню: Заказы";
    }

    @Override
    public List<MenuItem> items() {
        return List.of(
                new CommandItem(new CreateOrderFromCartCommand()),
                new CommandItem(new ListOrdersCommand()),
                new CommandItem(new ListOrdersByCustomerCommand()),
                new CommandItem(new ShowOrderCommand()),
                new CommandItem(new UpdateOrderStatusCommand())
        );
    }
}
