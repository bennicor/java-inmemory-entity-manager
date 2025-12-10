package org.example.shop.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.shop.AppConfig;
import org.example.shop.domain.model.Product;
import org.example.shop.domain.service.CartService;
import org.example.shop.domain.service.ProductService;
import org.example.shop.infrastructure.repository.db.Db;
import org.example.shop.infrastructure.repository.db.pg.PgCartRepository;
import org.example.shop.infrastructure.repository.db.pg.PgProductRepository;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private Connection conn;
    private ProductService productService;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            conn = Db.getConnection();
            PgProductRepository productRepo = new PgProductRepository();
            productService = new ProductService(productRepo);
            cartService = new CartService(new PgCartRepository(), productRepo);
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
            int page = 1;
            String pageParam = req.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Page value must not be null");
                }
            }

            int offset = (page - 1) * AppConfig.PRODUCTS_PER_PAGE;

            int totalProducts = productService.list().size();
            int totalPages = (int) Math.ceil((double) totalProducts / AppConfig.PRODUCTS_PER_PAGE);

            List<Product> products = productService.selectProductsForPage(offset, AppConfig.PRODUCTS_PER_PAGE);

            req.setAttribute("products", products);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.getRequestDispatcher("/product/list.jsp").forward(req, resp);
        } else if (action.equals("remove")) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            productService.remove(id);
            resp.sendRedirect("products?page=" + req.getParameter("page"));
        } else if (action.equals("new")) {
            req.getRequestDispatcher("/product/form.jsp").forward(req, resp);
        } else if (action.equals("view")) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            Product product = productService.findById(id);
            req.setAttribute("product", product);
            req.getRequestDispatcher("/product/view.jsp").forward(req, resp);
        } else if (action.equals("edit")) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            Optional<Product> product = Optional.ofNullable(productService.findById(id));
            req.setAttribute("product", product.orElse(null));
            req.getRequestDispatcher("/product/form.jsp").forward(req, resp);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String action = req.getParameter("action");
        if (action.equals("edit")) {
            productService.update(
                    Integer.parseInt(req.getParameter("id")),
                    req.getParameter("code"),
                    req.getParameter("name"),
                    Float.parseFloat(req.getParameter("price")),
                    Float.parseFloat(req.getParameter("weightKg")),
                    Integer.parseInt(req.getParameter("L").isBlank() ? "0" : req.getParameter("L")),
                    Integer.parseInt(req.getParameter("W").isBlank() ? "0" : req.getParameter("W")),
                    Integer.parseInt(req.getParameter("H").isBlank() ? "0" : req.getParameter("H")),
                    req.getParameter("description")
            );

            resp.sendRedirect("products");
        } else if (action.equals("new")) {
            productService.create(
                    req.getParameter("code"),
                    req.getParameter("name"),
                    Float.parseFloat(req.getParameter("price")),
                    Float.parseFloat(req.getParameter("weightKg")),
                    Integer.parseInt(req.getParameter("L").isBlank() ? "0" : req.getParameter("L")),
                    Integer.parseInt(req.getParameter("W").isBlank() ? "0" : req.getParameter("W")),
                    Integer.parseInt(req.getParameter("H").isBlank() ? "0" : req.getParameter("H")),
                    req.getParameter("description")
            );

            resp.sendRedirect("products");
        } else if (action.equals("toCart")) {
            cartService.add(
                    Integer.parseInt(req.getParameter("customerId")),
                    Integer.parseInt(req.getParameter("productId")),
                    Integer.parseInt(req.getParameter("amount"))
            );
        }
    }
}
