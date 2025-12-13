package tech.bobliu.assignment07.controller

import com.google.gson.Gson
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import tech.bobliu.assignment07.model.User
import tech.bobliu.assignment07.service.MessageService
import tech.bobliu.assignment07.service.UserService

@WebServlet(name = "messageServlet", value = ["/message"])
class MessageServlet : HttpServlet() {
    private val gson = Gson()

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "application/json;charset=UTF-8"

        val user = request.session.getAttribute("user") as User
        val afterId = request.getParameter("lastId")?.toIntOrNull()
        val messages = MessageService.getMessagesTo(user.id, afterId).map {
            mapOf(
                "id" to it.id,
                "type" to when {
                    it.senderId == -1 -> "system"
                    it.targetId == -1 -> "broadcast"
                    it.targetId == user.id -> "privateIn"
                    it.senderId == user.id -> "privateOut"
                    else -> "unknown"
                },
                "senderId" to it.senderId,
                "senderName" to if (it.senderId == -1) "系统" else UserService.getUserById(it.senderId).username,
                "targetId" to it.targetId,
                "targetName" to if (it.targetId == -1) "所有人" else UserService.getUserById(it.targetId).username,
                "content" to it.content,
                "timestamp" to it.time.time,
            )
        }

        response.writer.write(gson.toJson(mapOf("status" to "success", "messages" to messages)))
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "application/json;charset=UTF-8"

        val user = request.session.getAttribute("user") as User
        val message = gson.fromJson(request.reader, Map::class.java)
        val content = message["content"] as? String ?: ""
        val targetId = (message["targetId"] as? Double)?.toInt() ?: -1

        if (content.isEmpty()) {
            response.status = 400
            response.writer.write(gson.toJson(mapOf("status" to "error", "message" to "消息内容不能为空")))
        }

        val messageId = MessageService.sendMessage(content, user.id, targetId)
        response.writer.write(gson.toJson(mapOf("status" to "success", "messageId" to messageId)))
    }
}