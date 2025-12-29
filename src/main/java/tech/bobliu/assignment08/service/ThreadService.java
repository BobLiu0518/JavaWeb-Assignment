package tech.bobliu.assignment08.service;

import tech.bobliu.assignment08.model.Post;
import tech.bobliu.assignment08.model.Thread;
import tech.bobliu.assignment08.model.User;

import java.util.List;

public interface ThreadService {
    List<Thread> getAllThreads();
    Thread getThread(Long id);
    Thread createThread(String title, String content, User sender);
    Post reply(Long threadId, String content, User sender, Long replyToId);
}
