package org.example.shop.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.shop.domain.model.Cart;
import org.example.shop.domain.model.CartItem;
import org.example.shop.domain.model.Product;
import org.example.shop.domain.service.CartService;
import org.example.shop.domain.service.CustomerService;
import org.example.shop.domain.service.ProductService;
import org.example.shop.infrastructure.repository.db.Db;
import org.example.shop.infrastructure.repository.db.pg.PgCartRepository;
import org.example.shop.infrastructure.repository.db.pg.PgCustomerRepository;
import org.example.shop.infrastructure.repository.db.pg.PgProductRepository;

import java.io.IOException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Logger;

@WebServlet("/carts")
public class CartServlet extends HttpServlet {
    private CartService cartService;
    private CustomerService customerService;
    private ProductService productService;
    private Connection conn;
    private static final Logger LOGGER = Logger.getLogger(CartService.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            conn = Db.getConnection();
            cartService = new CartService(new PgCartRepository(), new PgProductRepository());
            customerService = new CustomerService(new PgCustomerRepository());
            productService = new ProductService(new PgProductRepository());
        } catch (Exception e) {
            throw new ServletException("Failed to initialize CustomerServlet: " + e.getMessage(), e);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");


        if (action == null || action.equals("list")) {
            List<Cart> carts = cartService.list();
            req.setAttribute("carts", carts);
            req.getRequestDispatcher("/cart/list.jsp").forward(req, resp);
        } else if (action.equals("view")) {
            Integer id = Integer.parseInt(req.getParameter("id"));

            Cart cart = cartService.getOrCreate(id);
            List<CartItem> items = cart.getItems();

            List<Map<String, Object>> cartDetails = new ArrayList<>();
            double totalCost = 0;
            for (CartItem item : items) {
                Map<String, Object> cartItemDetails = new HashMap<>();

                Product product = productService.findById(item.getProductId());
                cartItemDetails.put("id", item.getId());
                cartItemDetails.put("product", product);
                cartItemDetails.put("quantity", item.getQuantity());

                totalCost += product.getPrice() * item.getQuantity();
                cartDetails.add(cartItemDetails);
            }

            String deliveryCost = new DecimalFormat("#.##").format(Math.random() * 1000);
            req.setAttribute("cart", cart);
            req.setAttribute("cartDetails", cartDetails);
            req.setAttribute("customer", customerService.findById(id));
            req.setAttribute("deliveryCost", deliveryCost);
            req.setAttribute("totalCost", totalCost);
            req.getRequestDispatcher("/cart/view.jsp").forward(req, resp);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String action = req.getParameter("action");
        if (action.equals("add")) {
            cartService.add(
                    Integer.parseInt(req.getParameter("customerId")),
                    Integer.parseInt(req.getParameter("productId")),
                    Integer.parseInt(req.getParameter("amount"))
            );
        } else if (action.equals("reduce")) {
            cartService.add(
                    Integer.parseInt(req.getParameter("customerId")),
                    Integer.parseInt(req.getParameter("productId")),
                    Integer.parseInt(req.getParameter("amount")),
                    true
            );
        } else if (action.equals("removeFromCart")) {
            cartService.remove(
                    Integer.parseInt(req.getParameter("customerId")),
                    Integer.parseInt(req.getParameter("productId"))
            );
        }

        resp.sendRedirect("carts?action=view&id=" + req.getParameter("customerId"));
    }
}
