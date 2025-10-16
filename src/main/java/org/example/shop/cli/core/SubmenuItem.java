package org.example.shop.cli.core;

public class SubmenuItem implements MenuItem {
    private final String label;
    private final Menu submenu;

    public SubmenuItem(String label, Menu submenu) {
        this.label = label;
        this.submenu = submenu;
    }

    @Override
    public String label() {
        return label;
    }

    @Override
    public void trigger(MenuNavigator nav, Context ctx) {
        nav.push(submenu);
    }
}
