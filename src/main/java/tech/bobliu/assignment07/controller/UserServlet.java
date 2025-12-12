package tech.bobliu.assignment07.controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.bobliu.assignment07.model.User;
import tech.bobliu.assignment07.service.UserService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "userServlet", value = {"/user/*"})
public class UserServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getPathInfo();
        if ("/online".equals(path)) {
            response.setContentType("application/json;charset=UTF-8");
            User user = (User) request.getSession().getAttribute("user");
            List<Map<String, ? extends Serializable>> onlineUsers = UserService.getOnlineUsers().stream().map(u -> Map.of(
                    "id", u.getId(),
                    "username", u.getUsername(),
                    "isSelf", u.getId() == user.getId()
            )).collect(Collectors.toList());

            response.getWriter().write(gson.toJson(Map.of("status", "success", "users", onlineUsers)));
        }
    }
}
