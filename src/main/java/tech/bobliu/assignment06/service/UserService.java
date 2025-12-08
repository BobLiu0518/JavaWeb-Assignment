package tech.bobliu.assignment06.service;

import tech.bobliu.assignment06.dao.UserDao;
import tech.bobliu.assignment06.dao.UserDaoImpl;
import tech.bobliu.assignment06.model.User;

public class UserService {
    UserDao userDao = new UserDaoImpl();

    public UserService() {
        userDao.initUsersTable();
    }

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }
}
