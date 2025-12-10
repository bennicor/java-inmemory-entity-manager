package org.example.shop.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.shop.domain.model.*;
import org.example.shop.domain.service.OrderService;
import org.example.shop.domain.service.ProductService;
import org.example.shop.infrastructure.repository.db.Db;
import org.example.shop.infrastructure.repository.db.pg.PgCartRepository;
import org.example.shop.infrastructure.repository.db.pg.PgCustomerRepository;
import org.example.shop.infrastructure.repository.db.pg.PgOrderRepository;
import org.example.shop.infrastructure.repository.db.pg.PgProductRepository;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    private OrderService orderService;
    private ProductService productService;
    private Connection conn;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            conn = Db.getConnection();
            orderService = new OrderService(new PgOrderRepository(), new PgCustomerRepository(),
                    new PgProductRepository(), new PgCartRepository());
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
            List<Order> orders = orderService.list();
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/order/list.jsp").forward(req, resp);
        } else if (action.equals("view")) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            Order order = orderService.get(id);

            List<OrderItem> items = order.getItems();

            List<Map<String, Object>> orderDetails = new ArrayList<>();
            double totalCost = 0;
            for (OrderItem item : items) {
                Map<String, Object> orderItemDetails = new HashMap<>();

                Product product = productService.findById(item.getProductId());
                orderItemDetails.put("id", item.getId());
                orderItemDetails.put("product", product);
                orderItemDetails.put("quantity", item.getQuantity());
                orderItemDetails.put("nameSnapshot", item.getProductNameSnapshot());
                orderItemDetails.put("priceSnapshot", item.getUnitPrice());

                totalCost += item.getUnitPrice() * item.getQuantity();
                orderDetails.add(orderItemDetails);
            }

            req.setAttribute("order", order);
            req.setAttribute("orderDetails", orderDetails);
            req.setAttribute("deliveryCost", order.getDeliveryCost());
            req.setAttribute("totalCost", totalCost);
            req.getRequestDispatcher("/order/view.jsp").forward(req, resp);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String action = req.getParameter("action");

        if (action.equals("createOrderFromCart")) {
            Order order = orderService.createOrderFromCart(
                    Integer.parseInt(req.getParameter("customerId")),
                    Float.parseFloat(req.getParameter("deliveryCost")),
                    req.getParameter("paymentMethod")
            );
            resp.sendRedirect("orders?action=view&id=" + order.getId());
        } else if (action.equals("updateStatus")) {
            orderService.updateStatus(
                    Integer.parseInt(req.getParameter("orderId")),
                    req.getParameter("status")
            );
            resp.sendRedirect("orders");
        }
    }
}
