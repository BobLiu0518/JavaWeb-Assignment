package tech.bobliu.assignment08.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.bobliu.assignment08.model.Post;
import tech.bobliu.assignment08.model.Thread;
import tech.bobliu.assignment08.model.User;
import tech.bobliu.assignment08.repository.PostRepository;
import tech.bobliu.assignment08.repository.ThreadRepository;

import java.util.List;

@Service
public class ThreadServiceImpl implements ThreadService {

    private final ThreadRepository threadRepository;
    private final PostRepository postRepository;

    @Autowired
    public ThreadServiceImpl(ThreadRepository threadRepository, PostRepository postRepository) {
        this.threadRepository = threadRepository;
        this.postRepository = postRepository;
    }

    @Override
    public List<Thread> getAllThreads() {
        return threadRepository.findAll();
    }

    @Override
    public Thread getThread(Long id) {
        return threadRepository.findById(id).orElse(null);
    }

    @Override
    public Thread createThread(String title, String content, User sender) {
        Thread thread = new Thread();
        thread.setTitle(title);
        thread.setSender(sender);

        Post post = new Post();
        post.setSender(sender);
        post.setContent(content);
        post.setThread(thread);

        thread.getPosts().add(post);
        return threadRepository.save(thread);
    }

    @Override
    public Post reply(Long threadId, String content, User sender, Long replyToId) {
        Thread thread = threadRepository.findById(threadId)
                .orElseThrow(() -> new IllegalArgumentException("帖子不存在"));

        Post parentPost = null;
        if (replyToId != null) {
            parentPost = postRepository.findById(replyToId).orElse(null);
        }

        Post post = new Post();
        post.setSender(sender);
        post.setContent(content);
        post.setThread(thread);
        post.setParent(parentPost);

        return postRepository.save(post);
    }
}
