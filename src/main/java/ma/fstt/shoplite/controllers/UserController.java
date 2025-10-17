package ma.fstt.shoplite.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ma.fstt.shoplite.dtos.UserSessionResponse;
import ma.fstt.shoplite.entities.User;
import ma.fstt.shoplite.services.UserService;

import java.io.IOException;

@WebServlet(value = "/user")
public class UserController extends HttpServlet {

    @Inject
    UserService userService;

    String userView = "/WEB-INF/views/userView/";
    String authView = "/WEB-INF/views/authView/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        switch ((action != null) ? action : "login") {
            case "login":
                loginRequest(req, resp);
                break;
            case "profile":
                profileRequest(req, resp);
                break;
            case "register":
                registerRequest(req, resp);
                break;
            case "logout":
                logoutRequest(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch ((action != null) ? action : "login") {
            case "login":
                loginCheckRequest(req, resp);
                break;
            case "update":
                updateUserRequest(req, resp);
                break;
            case "register":
                registerCheckRequest(req, resp);
                break;
        }
    }

    void loginRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(authView + "login.jsp").forward(req, resp);
    }

    void loginCheckRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            UserSessionResponse user = userService.loginUserRequest(email, password);

            if (user != null) {
                HttpSession session = req.getSession(true);

                session.setAttribute("user", user);

                resp.sendRedirect(req.getContextPath() + "/");
            } else {
                req.setAttribute("error", "Invalid credentials.");
                req.getRequestDispatcher(authView + "login.jsp").forward(req, resp);
            }

            resp.sendRedirect(req.getContextPath());
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher(authView + "login.jsp").forward(req, resp);
        }

    }

    void registerRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(authView + "register.jsp").forward(req, resp);
    }

    void logoutRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        String referer = req.getHeader("referer");
        if (referer != null) {
            resp.sendRedirect(referer);
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    void registerCheckRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Long phoneNumber = Long.valueOf(req.getParameter("phoneNumber"));
        String address = req.getParameter("address");

        try {
            userService.registerUserRequest(firstName, lastName, email, password, phoneNumber, address);
            resp.sendRedirect(req.getContextPath());
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }
    }

    void updateUserRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            Long phoneNumber = Long.valueOf(req.getParameter("phoneNumber"));
            String address = req.getParameter("address");

            userService.updateUserRequest(firstName, lastName, email, password, phoneNumber, address);

            req.getSession().setAttribute("successMessage" , "Updated!");

            resp.sendRedirect(req.getContextPath()+ "/user?action=profile");
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
        }
    }

    void profileRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserSessionResponse userSession = (UserSessionResponse) req.getSession().getAttribute("user");

            if (userSession == null) {
                resp.sendRedirect(req.getContextPath() + "/user");
                return;
            }

            User userFullData = userService.getUser(userSession);

            req.setAttribute("user", userFullData);

            req.getRequestDispatcher(userView + "profile.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
