package org.example.shop.cli.commands.customer;

import org.example.shop.cli.core.Command;
import org.example.shop.cli.core.Context;
import org.example.shop.domain.model.Customer;

import java.util.List;

public class ListCustomersCommand implements Command {
    @Override
    public String name() {
        return "list-customers";
    }

    @Override
    public String description() {
        return "Вывести список клиентов";
    }

    @Override
    public String usage() {
        return "list-customers";
    }

    @Override
    public void execute(String[] args, Context ctx) {
        List<Customer> list = ctx.customers.list();
        if (list.isEmpty()) {
            System.out.println("(нет клиентов)");
            return;
        }

        for (Customer c : list) {
            System.out.printf("- id=%d firstName=%s lastName=%s email=%s%n",
                            c.getId(), c.getFirstName(), c.getLastName(), c.getEmail());
        }
    }
}
