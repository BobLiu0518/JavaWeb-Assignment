package tech.bobliu.assignment06.dao;

import tech.bobliu.assignment06.model.User;

public interface UserDao extends Dao {
    public void initUsersTable();

    public User getUserById(int id);

    public User getUserByUsername(String username);

    public void addUser(User user);
}
