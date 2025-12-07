package tech.bobliu.assignment06.dao;

import tech.bobliu.assignment06.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    public void initUsersTable() {
        try (Connection conn = Dao.getConnection()) {
            conn.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS users (
                        id SERIAL PRIMARY KEY,
                        username VARCHAR(50) NOT NULL,
                        passwordHash CHAR(60) NOT NULL,
                        UNIQUE(username)
                    )
                    """).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserById(int id) {
        try (Connection conn = Dao.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("""
                    SELECT id, username, passwordHash FROM users WHERE id = ?
                    """);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("passwordHash")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try (Connection conn = Dao.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("""
                    SELECT id, username, passwordHash FROM users WHERE username = ?
                    """);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("passwordHash")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(User user) {
        try (Connection conn = Dao.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("""
                    INSERT INTO users (username, passwordHash) VALUES (?, ?)
                    """, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                user.setId(generatedId);
            } else {
                throw new SQLException("Add user failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
