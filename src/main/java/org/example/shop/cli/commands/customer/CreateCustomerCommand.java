package org.example.shop.cli.commands.customer;

import org.example.shop.cli.core.Command;
import org.example.shop.cli.core.Context;
import org.example.shop.domain.model.Customer;

public class CreateCustomerCommand implements Command {
    @Override
    public String name() {
        return "create-customer";
    }

    @Override
    public String description() {
        return "Создать клиента";
    }

    @Override
    public String usage() {
        return "create-customer <firstName> <lastName> <email> <address>";
    }

    @Override
    public void execute(String[] args, Context ctx) {
        if (args.length < 4) {
            System.out.println("Недостаточно аргументов.");
            return;
        }

        Customer c = ctx.customers.create(args[1], "", args[2], "", args[3], args[4]);
        System.out.println("Создан клиент id=" + c.getId() + " | " + c.getLastName() + " | " + c.getEmail());
    }
}
