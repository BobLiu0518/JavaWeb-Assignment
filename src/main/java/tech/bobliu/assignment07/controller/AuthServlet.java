package tech.bobliu.assignment07.controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.bobliu.assignment07.model.User;
import tech.bobliu.assignment07.service.UserService;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "authServlet", value = {"/auth/*"})
public class AuthServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String path = request.getPathInfo();
            if ("/login".equals(path)) {
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            } else if ("/logout".equals(path)) {
                response.sendRedirect(request.getContextPath() + "/user/login");
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        try {
            String path = request.getPathInfo();
            if ("/login".equals(path)) {
                Map body = gson.fromJson(request.getReader(), Map.class);
                String username = (String) body.get("username");

                String error = null;
                if (username == null || username.isEmpty()) {
                    error = "用户名为空";
                } else if (username.length() < 4) {
                    error = "用户名至少 4 位";
                }

                if (error != null) {
                    response.setStatus(400);
                    response.getWriter().write(gson.toJson(Map.of("status", "error", "message", error)));
                    return;
                }

                User user = UserService.loginAsUser(username);
                request.getSession().setAttribute("user", user);
                request.getSession().setMaxInactiveInterval(60);

                response.getWriter().write(gson.toJson(Map.of("status", "success", "message", "登录成功")));
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
