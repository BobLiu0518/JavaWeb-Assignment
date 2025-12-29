package tech.bobliu.assignment08.service;

import tech.bobliu.assignment08.model.User;

public interface UserService {
    User register(String username, String password);
    User login(String username, String password);
}
