package org.example.shop.cli.menu;

import org.example.shop.cli.commands.customer.CreateCustomerCommand;
import org.example.shop.cli.commands.customer.GetCustomerCommand;
import org.example.shop.cli.commands.customer.ListCustomersCommand;
import org.example.shop.cli.core.CommandItem;
import org.example.shop.cli.core.Menu;
import org.example.shop.cli.core.MenuItem;

import java.util.List;

public class CustomerMenu implements Menu {
    @Override
    public String title() {
        return "Меню: Клиенты";
    }

    @Override
    public List<MenuItem> items() {
        return List.of(
                new CommandItem(new CreateCustomerCommand()),
                new CommandItem(new ListCustomersCommand()),
                new CommandItem(new GetCustomerCommand())
        );
    }
}
