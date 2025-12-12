package tech.bobliu.assignment07.controller;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import tech.bobliu.assignment07.model.User;
import tech.bobliu.assignment07.service.UserService;

@WebListener
public class UserSessionListener implements HttpSessionListener {
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        Object obj = se.getSession().getAttribute("user");
        if (obj instanceof User user) {
            UserService.setUserAsOffline(user);
        }
    }
}
