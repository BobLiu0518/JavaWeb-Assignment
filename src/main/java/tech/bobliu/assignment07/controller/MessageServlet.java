package tech.bobliu.assignment07.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.bobliu.assignment07.model.Message;
import tech.bobliu.assignment07.model.User;
import tech.bobliu.assignment07.service.MessageService;
import tech.bobliu.assignment07.service.UserService;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "messageServlet", value = {"/message"})
public class MessageServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        User user = (User) request.getSession().getAttribute("user");
        String afterIdParam = request.getParameter("lastId");
        Integer afterId = null;
        try {
            if (afterIdParam != null) afterId = Integer.parseInt(afterIdParam);
        } catch (NumberFormatException ignored) {
        }

        List<Message> messages = MessageService.getMessagesTo(user.getId(), afterId);
        List<Map<String, ? extends Serializable>> mapped = messages.stream().map(m -> {
            String type;
            if (m.getSenderId() == -1) {
                type = "system";
            } else if (m.getTargetId() == -1) {
                type = "broadcast";
            } else if (m.getTargetId() == user.getId()) {
                type = "privateIn";
            } else if (m.getSenderId() == user.getId()) {
                type = "privateOut";
            } else {
                type = "unknown";
            }

            String senderName = (m.getSenderId() == -1) ? "\u7cfb\u7edf" : UserService.getUserById(m.getSenderId()).getUsername();
            String targetName = (m.getTargetId() == -1) ? "\u6240\u6709\u4eba" : UserService.getUserById(m.getTargetId()).getUsername();

            return Map.of(
                    "id", m.getId(),
                    "type", type,
                    "senderId", m.getSenderId(),
                    "senderName", senderName,
                    "targetId", m.getTargetId(),
                    "targetName", targetName,
                    "content", m.getContent()
            );
        }).collect(Collectors.toList());

        response.getWriter().write(gson.toJson(Map.of("status", "success", "messages", mapped)));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        User user = (User) request.getSession().getAttribute("user");
        Type mapType = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> message = gson.fromJson(request.getReader(), mapType);
        String content = message.getOrDefault("content", "").toString();
        int targetId = -1;
        Object t = message.get("targetId");
        if (t instanceof Number) {
            targetId = ((Number) t).intValue();
        }

        if (content.isEmpty()) {
            response.setStatus(400);
            response.getWriter().write(gson.toJson(Map.of("status", "error", "message", "消息内容不能为空")));
            return;
        }

        int messageId = MessageService.sendMessage(content, user.getId(), targetId);
        response.getWriter().write(gson.toJson(Map.of("status", "success", "messageId", messageId)));
    }
}
