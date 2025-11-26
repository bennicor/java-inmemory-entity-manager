package org.example.shop.cli;

import org.example.shop.cli.core.Context;
import org.example.shop.cli.core.Menu;
import org.example.shop.cli.core.MenuItem;
import org.example.shop.cli.core.MenuNavigator;
import org.example.shop.cli.menu.CartMenu;
import org.example.shop.cli.menu.CustomerMenu;
import org.example.shop.cli.menu.MainMenu;
import org.example.shop.cli.menu.OrderMenu;
import org.example.shop.cli.menu.ProductMenu;

import java.util.List;

public class CliApplication {
    private final Context ctx;
    private final MenuNavigator navigator;

    public CliApplication(Context ctx) {
        this.ctx = ctx;
        this.navigator = createNavigator();
    }

    private MenuNavigator createNavigator() {
        ProductMenu productMenu = new ProductMenu();
        CustomerMenu customerMenu = new CustomerMenu();
        CartMenu cartMenu = new CartMenu();
        OrderMenu orderMenu = new OrderMenu();
        MainMenu mainMenu = new MainMenu(productMenu, customerMenu, cartMenu, orderMenu);
        return new MenuNavigator(mainMenu);
    }

    public void run() {
        System.out.println("Команды навигации: 'open <n>' — перейти; 'back' — назад; 'exit' — выход.");

        while (true) {
            Menu currentMenu = navigator.current();
            printMenu(currentMenu);

            if (!ctx.in.hasNextLine()) {
                break;
            }

            String line = ctx.in.nextLine().trim();

            if (line.equalsIgnoreCase("exit")) {
                break;
            }

            if (line.equalsIgnoreCase("back")) {
                navigator.back();
                continue;
            }

            if (line.startsWith("open")) {
                handleOpenCommand(line, currentMenu);
            } else {
                System.out.println("Неизвестно. Используйте 'open <n>', 'back', 'exit'.");
            }
        }
    }

    private void printMenu(Menu menu) {
        System.out.println("\n[" + menu.title() + "]");
        List<MenuItem> items = menu.items();

        for (int i = 0; i < items.size(); i++) {
            System.out.printf("  %d) %s%n", i + 1, items.get(i).label());
        }

        System.out.print("> ");
    }

    private void handleOpenCommand(String line, Menu currentMenu) {
        String[] parts = line.split("\\s+");

        if (parts.length < 2) {
            System.out.println("Используйте: open <номер пункта>");
            return;
        }

        List<MenuItem> items = currentMenu.items();

        try {
            int index = Integer.parseInt(parts[1]) - 1;

            if (index < 0 || index >= items.size()) {
                System.out.println("Нет такого пункта.");
                return;
            }

            try {
                items.get(index).trigger(navigator, ctx);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Номер пункта должен быть числом.");
        }
    }
}
