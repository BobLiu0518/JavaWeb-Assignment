package tech.bobliu.assignment07.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.bobliu.assignment07.model.User;
import tech.bobliu.assignment07.service.UserService;

import java.io.IOException;

@WebFilter(filterName = "00 - authFilter", value = {"/*"})
public class AuthFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null && ("/chat".equals(request.getServletPath()) || "/message".equals(request.getServletPath()))) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        if (user != null && "/auth".equals(request.getServletPath())) {
            UserService.setUserAsOffline(user);
            request.getSession().removeAttribute("user");
        }
        chain.doFilter(request, response);
    }
}
