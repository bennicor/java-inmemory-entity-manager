package org.example.shop.cli.core;

import java.util.List;

public interface Menu {
    String title();

    List<MenuItem> items();
}
