package org.example.shop.cli.core;

import java.util.ArrayDeque;
import java.util.Deque;

public class MenuNavigator {
    private final Deque<Menu> stack = new ArrayDeque<>();

    public MenuNavigator(Menu root) {
        stack.push(root);
    }

    public Menu current() {
        return stack.peek();
    }

    public void push(Menu m) {
        stack.push(m);
    }

    public void back() {
        if (stack.size() > 1) stack.pop();
    }

    public boolean atRoot() {
        return stack.size() == 1;
    }
}
