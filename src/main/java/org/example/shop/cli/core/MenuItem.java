package org.example.shop.cli.core;

public interface MenuItem {
    String label();

    void trigger(MenuNavigator nav, Context ctx) throws Exception;
}
