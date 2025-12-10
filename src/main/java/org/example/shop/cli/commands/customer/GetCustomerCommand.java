package org.example.shop.cli.commands.customer;

import org.example.shop.cli.core.Command;
import org.example.shop.cli.core.Context;
import org.example.shop.domain.model.Customer;

public class GetCustomerCommand implements Command {
    @Override
    public String name() {
        return "findById-customer";
    }

    @Override
    public String description() {
        return "Найти клиента по ID";
    }

    @Override
    public String usage() {
        return "findById-customer <id>";
    }

    @Override
    public void execute(String[] args, Context ctx) {
        if (args.length < 1) {
            System.out.println("Недостаточно аргументов.");
            return;
        }

        Customer c = ctx.customers.findById(Integer.parseInt(args[1]));
        System.out.println("Найден клиент id=" + c.getId() + " | " + c.getLastName() + " | " + c.getEmail());
    }
}
