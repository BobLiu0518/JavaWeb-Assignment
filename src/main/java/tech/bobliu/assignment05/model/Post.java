package tech.bobliu.assignment05.model;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    private Date datetime = null;
    private User sender = null;
    private String content = null;
    private int replyTo = -1;

    public Post() {
    }

    public Post(User sender, String content, int replyTo) {
        this.datetime = new Date();
        this.sender = sender;
        this.content = content;
        this.replyTo = replyTo;
    }

    public Date getDatetime() {
        return datetime;
    }

    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public int getReplyTo() {
        return replyTo;
    }
}
