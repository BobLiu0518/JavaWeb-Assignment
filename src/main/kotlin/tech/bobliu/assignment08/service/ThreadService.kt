package tech.bobliu.assignment08.service

import tech.bobliu.assignment08.model.Post
import tech.bobliu.assignment08.model.Thread
import tech.bobliu.assignment08.model.User

interface ThreadService {
    fun getAllThreads(): List<Thread>
    fun getThread(id: Long): Thread?
    fun createThread(title: String, content: String, sender: User): Thread
    fun reply(threadId: Long, content: String, sender: User, replyToId: Long? = null): Post
}
