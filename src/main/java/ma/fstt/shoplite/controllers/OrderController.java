package ma.fstt.shoplite.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.fstt.shoplite.dtos.UserSessionResponse;
import ma.fstt.shoplite.entities.Order;
import ma.fstt.shoplite.services.OrderService;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/order")
public class OrderController extends HttpServlet {
    @Inject
    OrderService orderService;

    String orderView = "/WEB-INF/views/orderView/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        switch ((action == null || action.isEmpty()) ? "list" : action) {
            case "add":
                addOrderRequest(req, resp);
                break;
            case "list" :
                listOrdersRequest(req, resp);
                break;
            case "show":
                showOrderRequest(req, resp);
            break;
        }
    }

    void addOrderRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserSessionResponse userSessionResponse = (UserSessionResponse) req.getSession().getAttribute("user");

            orderService.saveOrder(userSessionResponse.getId());

            req.getSession().setAttribute("successMessage" , "Created");
            resp.sendRedirect(req.getContextPath() + "/order");
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage" , e.getMessage());
            req.getRequestDispatcher(orderView + "orderList.jsp").forward(req, resp);
        }
    }

    void listOrdersRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserSessionResponse userSessionResponse = (UserSessionResponse) req.getSession().getAttribute("user");

        if(userSessionResponse == null) {
            resp.sendRedirect(req.getContextPath() + "/user");
        }

        List<Order> order = orderService.listOrdersByUserId(userSessionResponse.getId());

        req.setAttribute("orders", order);
        req.getRequestDispatcher(orderView + "orderList.jsp").forward(req, resp);
    }

    void showOrderRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserSessionResponse userSessionResponse = (UserSessionResponse) req.getSession().getAttribute("user");

        Long orderId = Long.parseLong(req.getParameter("id"));

        Order order = orderService.showOrderById(orderId);

        req.setAttribute("order", order);
        req.getRequestDispatcher(orderView + "order.jsp").forward(req, resp);
    }
}
