package ma.fstt.shoplite.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ma.fstt.shoplite.dtos.UserSessionResponse;
import ma.fstt.shoplite.entities.Cart;
import ma.fstt.shoplite.services.CartService;
import ma.fstt.shoplite.utils.BusinessException;

import java.io.IOException;

@WebServlet(value = "/cart")
public class CartController extends HttpServlet {

    @Inject
    CartService cartService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = (req.getParameter("action") == null || req.getParameter("action").isEmpty())
                ? "list" : req.getParameter("action");

        switch (action) {
            case "add":
                handleAddToCart(req, resp);
                break;
            case "list":
            default:
                handleListCart(req, resp);
                break;
            case "delete" :
                deleteCartItem(resp, req);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if ("update".equals(action)) {
            handleUpdateCart(req, resp);
        }
    }

    private void handleUpdateCart(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!ensureAuthenticated(req, resp)) return;
        UserSessionResponse user = getLoggedInUser(req);


        try {
            Long productId = parseLong(req, "productId");
            Integer quantity = parseInt(req, "quantity");

            cartService.saveOrUpdateCart(user, productId, quantity);
            sendJsonResponse(resp, HttpServletResponse.SC_OK, "{\"status\":\"ok\"}");

        } catch (BusinessException e) {
            sendJsonResponse(resp, HttpServletResponse.SC_NOT_ACCEPTABLE, "{\"status\":\"error\"}");
        } catch (Exception e) {
            e.printStackTrace();
            setSessionMessage(req, "errorMessage", "Failed to add product to cart.");
            redirectToProduct(resp, req, req.getParameter("id"));
        }
    }

    private UserSessionResponse getLoggedInUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (UserSessionResponse) session.getAttribute("user") : null;
    }

    private boolean ensureAuthenticated(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserSessionResponse user = getLoggedInUser(req);
        if (user == null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("linkAfterLogin", req.getHeader("referer"));
            resp.sendRedirect(req.getContextPath() + "/user");
            return false;
        }
        return true;
    }

    private void handleAddToCart(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!ensureAuthenticated(req, resp)) return;
        UserSessionResponse user = getLoggedInUser(req);


        try {
            Long productId = parseLong(req, "id");
            Integer quantity = parseInt(req, "qty");

            cartService.saveOrUpdateCart(user, productId, quantity);
            setSessionMessage(req, "successMessage", "Product added to cart successfully!");
            redirectToProduct(resp, req, productId);

        } catch (BusinessException e) {
            handleBusinessError(req, resp, e);

        } catch (Exception e) {
            e.printStackTrace();
            setSessionMessage(req, "errorMessage", "Failed to add product to cart.");
            redirectToProduct(resp, req, req.getParameter("id"));
        }
    }

    private void handleListCart(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        UserSessionResponse user = (UserSessionResponse) req.getSession().getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/user");
            return;
        }

        Cart cart = cartService.findByUserId(user.getId());
        req.setAttribute("cart", cart);
        req.setAttribute("cartTotal", calculateCartTotal(cart));

        req.getRequestDispatcher("/WEB-INF/views/cartView/cartList.jsp").forward(req, resp);
    }

    private double calculateCartTotal(Cart cart) {
        if (cart == null || cart.getCartItems() == null) return 0.0;

        return cart.getCartItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
    }

    private void setSessionMessage(HttpServletRequest req, String key, String value) {
        req.getSession().setAttribute(key, value);
    }

    private void handleBusinessError(HttpServletRequest req, HttpServletResponse resp, BusinessException e)
            throws IOException {

        switch (e.getError()) {
            case PRODUCT_NOT_FOUND:
                setSessionMessage(req, "errorMessage", "Product not found");
                break;
            case PRODUCT_INSUFFICIENT_QUANTITY:
                setSessionMessage(req, "errorMessage", "Insufficient quantity in stock");
                break;
            default:
                setSessionMessage(req, "errorMessage", "An unexpected error occurred");
        }

        redirectToProduct(resp, req, req.getParameter("id"));
    }

    private void redirectToProduct(HttpServletResponse resp, HttpServletRequest req, Object productId)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + "/product?action=show&id=" + productId);
    }

    private Integer parseInt(HttpServletRequest req, String paramName) {
        try {
            return Integer.parseInt(req.getParameter(paramName));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid quantity: " + req.getParameter(paramName));
        }
    }

    private Long parseLong(HttpServletRequest req, String paramName) {
        try {
            return Long.parseLong(req.getParameter(paramName));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid product ID: " + req.getParameter(paramName));
        }
    }

    private void sendJsonResponse(HttpServletResponse resp, int statusCode, String json) throws IOException {
        resp.setStatus(statusCode);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println(json);
    }

    public void deleteCartItem(HttpServletResponse resp, HttpServletRequest req) {
        Long productId = parseLong(req, "productId");
        UserSessionResponse user = getLoggedInUser(req);
        try {
            cartService.deleteCartItem(user, productId);
            resp.sendRedirect(req.getContextPath() + "/cart");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }
    }
}
