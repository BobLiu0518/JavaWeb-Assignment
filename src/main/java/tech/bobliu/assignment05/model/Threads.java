package tech.bobliu.assignment05.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Threads implements Serializable {
    private final Map<Integer, Thread> threads = new ConcurrentSkipListMap<>();
    private final AtomicInteger maxThreadId = new AtomicInteger(0);

    public Threads() {
    }

    public Thread addThread(User sender, String title, String content) {
        Thread thread = new Thread(sender, title, content, this.maxThreadId.incrementAndGet());
        this.threads.put(thread.getThreadId(), thread);
        return thread;
    }

    public Thread getThread(int id) {
        Thread thread = this.threads.get(id);
        if (thread == null) {
            throw new IllegalArgumentException("帖子不存在");
        }
        return thread;
    }

    public Map<Integer, Thread> getThreads() {
        return threads;
    }

    public void addPostToThread(int threadId, User sender, String content, int replyTo) {
        Thread thread = this.threads.get(threadId);
        Post post = new Post(sender, content, replyTo);
        thread.getPosts().add(post);
    }
}
