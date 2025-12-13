package tech.bobliu.assignment07.service;

import tech.bobliu.assignment07.model.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MessageService {
    private static final List<Message> messages = new ArrayList<>();

    public static List<Message> getMessagesTo(int targetId, Integer afterId) {
        int start = (afterId == null ? -1 : afterId) + 1;
        return messages.stream()
                .skip(start)
                .filter(m -> m.getTargetId() == targetId || m.getSenderId() == targetId || m.getTargetId() == -1)
                .collect(Collectors.toList());
    }

    public static int sendMessage(String content, int senderId, int targetId) {
        synchronized (messages) {
            Message message = new Message(messages.size(), senderId, targetId, content, new Date());
            messages.add(message);
            return message.getId();
        }
    }
}
