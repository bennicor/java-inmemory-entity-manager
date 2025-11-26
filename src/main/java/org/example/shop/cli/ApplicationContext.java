package org.example.shop.cli;

import org.example.shop.cli.core.Context;
import org.example.shop.domain.repository.CartRepository;
import org.example.shop.domain.repository.CustomerRepository;
import org.example.shop.domain.repository.OrderRepository;
import org.example.shop.domain.repository.ProductRepository;
import org.example.shop.domain.service.CartService;
import org.example.shop.domain.service.CustomerService;
import org.example.shop.domain.service.OrderService;
import org.example.shop.domain.service.ProductService;
import org.example.shop.infrastructure.repository.db.pg.PgCartRepository;
import org.example.shop.infrastructure.repository.db.pg.PgCustomerRepository;
import org.example.shop.infrastructure.repository.db.pg.PgOrderRepository;
import org.example.shop.infrastructure.repository.db.pg.PgProductRepository;

import java.util.Scanner;

public class ApplicationContext {
    public static Context getContext(Scanner in) {
        ProductRepository productRepo = new PgProductRepository();
        CustomerRepository customerRepo = new PgCustomerRepository();
        CartRepository cartRepo = new PgCartRepository();
        OrderRepository orderRepo = new PgOrderRepository();

        ProductService products = new ProductService(productRepo);
        CustomerService customers = new CustomerService(customerRepo);
        CartService carts = new CartService(cartRepo, productRepo);
        OrderService orders = new OrderService(orderRepo, customerRepo, productRepo, cartRepo);

        return new Context(products, customers, carts, orders, in);
    }
}
