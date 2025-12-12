package tech.bobliu.assignment05.model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Base64;

public class User implements Serializable {
    private String username = null;
    private String passwordHash = null;
    private int userId = -1;

    public User() {
    }

    public User(String username, String password, int userId) {
        this.setUsername(username);
        this.setPassword(password);
        this.setUserId(userId);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().length() < 8) {
            throw new IllegalArgumentException("密码至少 8 位");
        }
        this.passwordHash = hashPassword(password.trim());
    }

    public boolean verifyPassword(String password) {
        if (password == null || this.passwordHash == null) {
            return false;
        }
        return this.passwordHash.equals(hashPassword(password.trim()));
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private static String hashPassword(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
