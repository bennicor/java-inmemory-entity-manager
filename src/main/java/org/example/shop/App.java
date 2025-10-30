package org.example.shop;

import org.example.shop.cli.core.Context;
import org.example.shop.cli.core.Menu;
import org.example.shop.cli.core.MenuItem;
import org.example.shop.cli.core.MenuNavigator;
import org.example.shop.cli.menu.*;
import org.example.shop.domain.service.CartService;
import org.example.shop.domain.service.CustomerService;
import org.example.shop.domain.service.OrderService;
import org.example.shop.domain.service.ProductService;
import org.example.shop.infrastructure.repository.InMemoryCartRepository;
import org.example.shop.infrastructure.repository.InMemoryCustomerRepository;
import org.example.shop.infrastructure.repository.InMemoryOrderRepository;
import org.example.shop.infrastructure.repository.InMemoryProductRepository;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Context ctx = getContext();

        populateWithTestData(ctx);

        ProductMenu productMenu = new ProductMenu();
        CustomerMenu customerMenu = new CustomerMenu();
        CartMenu cartMenu = new CartMenu();
        OrderMenu orderMenu = new OrderMenu();
        MainMenu mainMenu = new MainMenu(productMenu, customerMenu, cartMenu, orderMenu);

        MenuNavigator nav = new MenuNavigator(mainMenu);

        System.out.println("Команды навигации: 'open <n>' — перейти; 'back' — назад; 'exit' — выход.");
        while (true) {
            Menu m = nav.current();
            System.out.println("\n[" + m.title() + "]");
            List<MenuItem> items = m.items();
            for (int i = 0; i < items.size(); i++) {
                System.out.printf("  %d) %s%n", i + 1, items.get(i).label());
            }
            System.out.print("> ");

            if (!ctx.in.hasNextLine()) {
                break;
            }

            String line = ctx.in.nextLine().trim();

            if (line.equalsIgnoreCase("exit")) {
                break;
            }

            if (line.equalsIgnoreCase("back")) {
                nav.back();
                continue;
            }

            if (line.startsWith("open")) {
                String[] parts = line.split(" ");

                if (parts.length < 2) {
                    System.out.println("Используйте: open <номер пункта>");
                    continue;
                }

                try {
                    int index = Integer.parseInt(parts[1]) - 1;

                    if (index < 0 || index >= items.size()) {
                        System.out.println("Нет такого пункта.");
                        continue;
                    }

                    try {
                        items.get(index).trigger(nav, ctx);
                    } catch (Exception e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Номер пункта должен быть числом.");
                }
            } else {
                System.out.println("Неизвестно. Используйте 'open <n>', 'back', 'exit'.");
            }
        }
    }

    private static void populateWithTestData
            (Context ctx) {
        ctx.products.create("P001", "Phone", 299.99f, 0.3f, 0, 0, 0, "phone");
        ctx.products.create("P002", "Laptop", 799.99f, 2.3f, 0, 0, 0, "laptop");
        ctx.products.create("P003", "Tablet", 199.99f, 2.5f, 0, 0, 0, "Its tablet time");
        ctx.products.create("P004", "Headphones", 59.99f, 1.4f, 0, 0, 0, "Its music time");

        ctx.customers.create("Ivanov", "Ivan", "Ivanovich", "123 Main St", "123456789", "ivanov@email.com");
        ctx.customers.create("Petrov", "Petr", "Petrovich", "456 Second St", "987654321", "petrov@email.com");

        ctx.carts.add(1, 1, 2);
        ctx.carts.add(1, 2, 7);
        ctx.carts.add(1, 3, 4);
        ctx.carts.add(2, 4, 1);
        ctx.carts.add(2, 1, 2);

        ctx.orders.createOrderFromCart(1, 20.00f, "CARD");
        ctx.orders.createOrderFromCart(2, 27.00f, "CASH");
    }

    private static Context getContext() {
        InMemoryProductRepository productRepo = new InMemoryProductRepository();
        InMemoryCustomerRepository customerRepo = new InMemoryCustomerRepository();
        InMemoryCartRepository cartRepo = new InMemoryCartRepository();
        InMemoryOrderRepository orderRepo = new InMemoryOrderRepository();

        ProductService products = new ProductService(productRepo);
        CustomerService customers = new CustomerService(customerRepo);
        CartService carts = new CartService(cartRepo, productRepo);
        OrderService orders = new OrderService(orderRepo, customerRepo, productRepo, cartRepo);

        return new Context(products, customers, carts, orders, new Scanner(System.in));
    }
}
