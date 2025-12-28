package tech.bobliu.assignment08.service

import org.springframework.stereotype.Service
import tech.bobliu.assignment08.model.Post
import tech.bobliu.assignment08.model.Thread
import tech.bobliu.assignment08.model.User
import tech.bobliu.assignment08.repository.PostRepository
import tech.bobliu.assignment08.repository.ThreadRepository

@Service
class ThreadService(
    private val threadRepository: ThreadRepository,
    private val postRepository: PostRepository
) {

    fun getAllThreads(): List<Thread> {
        return threadRepository.findAll()
    }

    fun getThread(id: Long): Thread? {
        return threadRepository.findById(id).orElse(null)
    }

    fun createThread(title: String, content: String, sender: User): Thread {
        val thread = Thread(title = title, sender = sender)
        val post = Post(sender = sender, content = content, thread = thread)
        thread.posts.add(post)
        return threadRepository.save(thread)
    }

    fun reply(threadId: Long, content: String, sender: User, replyToId: Long? = null): Post {
        val thread = threadRepository.findById(threadId).orElseThrow { IllegalArgumentException("帖子不存在") }
        var parentPost: Post? = null
        if (replyToId != null) {
            parentPost = postRepository.findById(replyToId).orElse(null)
        }
        val post = Post(sender = sender, content = content, thread = thread, parent = parentPost)
        return postRepository.save(post)
    }
}
