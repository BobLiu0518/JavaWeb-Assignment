package tech.bobliu.assignment05.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;

public class Thread implements Serializable {
    private String title = null;
    private Date datetime = null;
    private User sender = null;
    private List<Post> posts = null;
    private int threadId = -1;

    public Thread() {
    }

    public Thread(User sender, String title, String content, int threadId) {
        this.title = title;
        this.datetime = new Date();
        this.sender = sender;
        this.posts = Collections.synchronizedList(new ArrayList<>());
        Post post = new Post(sender, content, -1);
        this.posts.add(post);
        this.threadId = threadId;
    }

    public String getTitle() {
        return title;
    }

    public Date getDatetime() {
        return datetime;
    }

    public User getSender() {
        return sender;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public int getThreadId() {
        return threadId;
    }
}
