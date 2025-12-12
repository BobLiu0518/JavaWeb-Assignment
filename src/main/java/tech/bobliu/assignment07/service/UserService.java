package tech.bobliu.assignment07.service;

import tech.bobliu.assignment07.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService {
    private static final List<User> users = new ArrayList<>();
    private static final Map<Integer, User> onlineUsers = new HashMap<>();

    public static User getUserById(int id) {
        return users.get(id);
    }

    public static User getUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }

    public static List<User> getOnlineUsers() {
        return new ArrayList<>(onlineUsers.values());
    }

    public static User loginAsUser(String username) {
        User user;
        synchronized (users) {
            User found = getUserByUsername(username);
            if (found != null) {
                user = found;
            } else {
                user = new User(users.size(), username);
                users.add(user);
            }
        }
        synchronized (onlineUsers) {
            onlineUsers.put(user.getId(), user);
        }

        MessageService.sendMessage("@" + user.getUsername() + " 已加入聊天室", -1, -1);
        String onlineList = getOnlineUsers().stream().map(u -> "@" + u.getUsername()).collect(Collectors.joining(" "));
        MessageService.sendMessage("当前在线用户：" + onlineList, -1, -1);

        return user;
    }

    public static void setUserAsOffline(User user) {
        MessageService.sendMessage("@" + user.getUsername() + " 已离开聊天室", -1, -1);
        synchronized (onlineUsers) {
            onlineUsers.remove(user.getId());
        }
    }
}
