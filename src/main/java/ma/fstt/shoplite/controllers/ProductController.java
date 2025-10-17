package ma.fstt.shoplite.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.fstt.shoplite.entities.Product;
import ma.fstt.shoplite.services.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/product")
public class ProductController extends HttpServlet {

    @Inject
    ProductService productService;

    String productView = "/WEB-INF/views/productView/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        switch ((action != null) ? action : "list") {
            case "show":
                showProductRequest(req, resp);
                break;
            case "list":
                listProductsResquest(req, resp);
                break;
            case "search":
                searchProductRequest(req, resp);
                break;
            default:
                req.getRequestDispatcher(productView + "productNotFound.jsp").forward(req, resp);
                break;
        }
    }

    void showProductRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long productId = Long.parseLong(req.getParameter("id"));
            Product product = productService.findProductById(productId);
            req.setAttribute("product", product);
            req.getRequestDispatcher(productView + "product.jsp").forward(req, resp);
        } catch (Exception e) {
            //req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher(productView + "productNotFound.jsp").forward(req, resp);
        }
    }

    void listProductsResquest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Product> products = productService.findAll();
            req.setAttribute("products", products);
            req.getRequestDispatcher(productView + "productList.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());

        }
    }

    void searchProductRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String term = req.getParameter("term");

        try {
            List<Product> products = productService.searchByLabel(term);

            req.setAttribute("products", products);
            req.getRequestDispatcher(productView + "productList.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher(productView + "productList.jsp").forward(req, resp);        }

    }
}
