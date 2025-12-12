package tech.bobliu.assignment05.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Users implements Serializable {
    private final Map<String, User> users = new ConcurrentSkipListMap<>();
    private AtomicInteger maxUserId = new AtomicInteger(0);

    public Users() {
    }

    public User getUser(String username) {
        User user = this.users.get(username);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        return user;
    }

    public User addUser(String username, String password) {
        if (this.users.containsKey(username)) {
            throw new IllegalArgumentException("用户已存在");
        }

        User user = new User(username, password, this.maxUserId.incrementAndGet());
        this.users.put(username, user);
        return user;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public User verifyUser(String username, String password) {
        User user = this.getUser(username);
        if (!user.verifyPassword(password)) {
            throw new IllegalArgumentException("密码错误");
        }
        return user;
    }
}
