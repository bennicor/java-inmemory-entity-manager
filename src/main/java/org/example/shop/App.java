package org.example.shop;

import org.example.shop.cli.core.Context;
import org.example.shop.cli.core.Menu;
import org.example.shop.cli.core.MenuItem;
import org.example.shop.cli.core.MenuNavigator;
import org.example.shop.cli.menu.CartMenu;
import org.example.shop.cli.menu.CustomerMenu;
import org.example.shop.cli.menu.MainMenu;
import org.example.shop.cli.menu.ProductMenu;
import org.example.shop.cli.menu.OrderMenu;
import org.example.shop.domain.service.CartService;
import org.example.shop.domain.service.CustomerService;
import org.example.shop.domain.service.OrderService;
import org.example.shop.domain.service.ProductService;
import org.example.shop.infrastructure.repository.db.pg.PgOrderRepository;
import org.example.shop.infrastructure.repository.db.pg.PgCustomerRepository;
import org.example.shop.infrastructure.repository.db.pg.PgCartRepository;
import org.example.shop.infrastructure.repository.db.pg.PgProductRepository;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Context ctx = getContext();

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

    private static Context getContext() {
        var productRepo = new PgProductRepository();
        var customerRepo = new PgCustomerRepository();
        var cartRepo = new PgCartRepository();
        var orderRepo = new PgOrderRepository();

        var products = new ProductService(productRepo);
        var customers = new CustomerService(customerRepo);
        var carts = new CartService(cartRepo, productRepo);
        var orders = new OrderService(orderRepo, customerRepo, productRepo, cartRepo);

        return new Context(products, customers, carts, orders, new Scanner(System.in));
    }
}
