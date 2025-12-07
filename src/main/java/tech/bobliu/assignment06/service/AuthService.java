package tech.bobliu.assignment06.service;

import org.mindrot.jbcrypt.BCrypt;
import tech.bobliu.assignment06.dao.UserDao;
import tech.bobliu.assignment06.dao.UserDaoImpl;
import tech.bobliu.assignment06.model.User;

public class AuthService {
    UserDao userDao = new UserDaoImpl();

    public AuthService() {
        userDao.initUsersTable();
    }

    public User login(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("用户名和密码不能为空");
        }
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (!BCrypt.checkpw(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("密码错误");
        }
        return user;
    }

    public User register(String username, String password) {
        if (userDao.getUserByUsername(username) != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        if (username == null || password == null) {
            throw new IllegalArgumentException("用户名和密码不能为空");
        }
        if (username.length() < 4) {
            throw new IllegalArgumentException("用户名长度至少为4位");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("密码长度至少为8位");
        }
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(0, username, passwordHash);
        userDao.addUser(user);
        return user;
    }
}
