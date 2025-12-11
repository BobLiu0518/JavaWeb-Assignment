package tech.bobliu.assignment07.service

import tech.bobliu.assignment07.model.Message

object MessageService {
    private val messages: ArrayList<Message> = ArrayList()

    fun getMessagesTo(targetId: Int, afterId: Int?) = messages
        .drop((afterId ?: -1) + 1).filter { it.targetId == targetId || it.senderId == targetId || it.targetId == -1 }

    fun sendMessage(content: String, senderId: Int, targetId: Int): Int {
        synchronized(messages) {
            val message = Message(
                id = messages.size,
                senderId = senderId,
                targetId = targetId,
                content = content,
            )
            messages.add(message)
            return message.id
        }
    }
}