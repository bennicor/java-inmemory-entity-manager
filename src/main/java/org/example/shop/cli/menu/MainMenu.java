package org.example.shop.cli.menu;

import org.example.shop.cli.core.Menu;
import org.example.shop.cli.core.MenuItem;
import org.example.shop.cli.core.SubmenuItem;

import java.util.List;

public class MainMenu implements Menu {
    private final Menu products, customers, cart, orders;

    public MainMenu(Menu products, Menu customers, Menu cart, Menu orders) {
        this.products = products;
        this.customers = customers;
        this.cart = cart;
        this.orders = orders;
    }

    @Override
    public String title() {
        return "Главное меню";
    }

    @Override
    public List<MenuItem> items() {
        return List.of(
                new SubmenuItem("Товары →", products),
                new SubmenuItem("Клиенты →", customers),
                new SubmenuItem("Корзина →", cart),
                new SubmenuItem("Заказы →", orders)
        );
    }
}
