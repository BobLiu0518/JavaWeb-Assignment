package tech.bobliu.assignment06.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.bobliu.assignment06.model.User;
import tech.bobliu.assignment06.service.AuthService;

import java.io.IOException;

@WebServlet(name = "authServlet", value = "/auth/*", loadOnStartup = 1)
public class AuthServlet extends HttpServlet {
    AuthService authService = new AuthService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo.equals("/login")) {
            request.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        } else if (pathInfo.equals("/register")) {
            request.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
        } else if (pathInfo.equals("/logout")) {
            request.getSession().invalidate();
            request.setAttribute("message", "登出成功");
            request.getServletContext().getRequestDispatcher("/success.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = null;
        String message = null;

        try {
            if (pathInfo.equals("/login")) {
                user = authService.login(username, password);
                message = "登录成功";
            } else if (pathInfo.equals("/register")) {
                user = authService.register(username, password);
                message = "注册成功";
            }
            request.getSession().setAttribute("user", user);
            request.setAttribute("message", message);
            request.getServletContext().getRequestDispatcher("/success.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("message", e.getMessage());
            request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}