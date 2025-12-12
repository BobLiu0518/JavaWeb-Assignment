package tech.bobliu.assignment05.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tech.bobliu.assignment05.model.User;
import tech.bobliu.assignment05.model.Users;

import java.io.IOException;

@WebServlet(name = "authServlet", value = "/auth/*", loadOnStartup = 1)
public class AuthServlet extends HttpServlet {
    public void init() {
        ServletContext servletContext = getServletContext();
        servletContext.setAttribute("users", new Users());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession();
        ServletContext servletContext = getServletContext();
        Users users = (Users) servletContext.getAttribute("users");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String captcha = request.getParameter("captcha");
        String captchaResult = (String) session.getAttribute("captchaResult");

        try {
            if (pathInfo.equals("/logout")) {
                session.setAttribute("user", null);
                request.setAttribute("message", "登出成功");
            } else {
                if (username == null || password == null) {
                    throw new IllegalArgumentException("用户名和密码不能为空");
                }
                if (captcha == null || !captcha.equals(captchaResult)) {
                    throw new IllegalArgumentException("验证码错误");
                }

                if (pathInfo.equals("/login")) {
                    User user = users.verifyUser(username, password);
                    session.setAttribute("user", user);
                    request.setAttribute("message", "欢迎，" + user.getUsername());
                } else {
                    User newUser = users.addUser(username, password);
                    session.setAttribute("user", newUser);
                    request.setAttribute("message", "注册成功，欢迎，" + newUser.getUsername());
                }
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("message", e.getMessage());
            RequestDispatcher rd = servletContext.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
        }

        RequestDispatcher rd = servletContext.getRequestDispatcher("/success.jsp");
        rd.forward(request, response);
    }
}