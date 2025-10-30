package org.example.shop.cli.core;

import org.example.shop.domain.service.CartService;
import org.example.shop.domain.service.CustomerService;
import org.example.shop.domain.service.OrderService;
import org.example.shop.domain.service.ProductService;

import java.util.Scanner;

public class Context {
    public final ProductService products;
    public final CustomerService customers;
    public final CartService carts;
    public final OrderService orders;

    public final Scanner in;

    public Context(ProductService p, CustomerService c, CartService cart, OrderService o,
                   Scanner in) {
        this.products = p;
        this.customers = c;
        this.carts = cart;
        this.orders = o;
        this.in = in;
    }
}
