package org.example.shop.servlet;

import org.example.shop.domain.model.Customer;
import org.example.shop.domain.service.CartService;
import org.example.shop.domain.service.CustomerService;
import org.example.shop.infrastructure.repository.db.Db;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.shop.infrastructure.repository.db.pg.PgCartRepository;
import org.example.shop.infrastructure.repository.db.pg.PgCustomerRepository;
import org.example.shop.infrastructure.repository.db.pg.PgProductRepository;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {
    private Connection conn;
    private CustomerService customerService;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            conn = Db.getConnection();
            customerService = new CustomerService(new PgCustomerRepository());
            cartService = new CartService(new PgCartRepository(), new PgProductRepository());
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
            List<Customer> customers = customerService.list();
            req.setAttribute("customers", customers);
            req.getRequestDispatcher("/customer/list.jsp").forward(req, resp);
        } else if (action.equals("remove")) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            customerService.remove(id);
            resp.sendRedirect("customers");
        } else if (action.equals("new")) {
            req.getRequestDispatcher("/customer/form.jsp").forward(req, resp);
        } else if (action.equals("view")) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            Customer customer = customerService.findById(id);
            req.setAttribute("customer", customer);
            req.getRequestDispatcher("/customer/view.jsp").forward(req, resp);
        } else if (action.equals("edit")) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            Optional<Customer> customer = Optional.ofNullable(customerService.findById(id));
            req.setAttribute("customer", customer.orElse(null));
            req.getRequestDispatcher("/customer/form.jsp").forward(req, resp);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String action = req.getParameter("action");
        if (action.equals("edit")) {
            customerService.update(
                    Integer.parseInt(req.getParameter("id")),
                    req.getParameter("firstName"),
                    req.getParameter("middleName"),
                    req.getParameter("lastName"),
                    req.getParameter("phone"),
                    req.getParameter("email"),
                    req.getParameter("address")
            );
        } else if (action.equals("new")) {
            Customer customer = customerService.create(
                    req.getParameter("firstName"),
                    req.getParameter("middleName"),
                    req.getParameter("lastName"),
                    req.getParameter("phone"),
                    req.getParameter("email"),
                    req.getParameter("address")
            );
            cartService.getOrCreate(customer.getId());
        }

        resp.sendRedirect("customers");
    }
}
