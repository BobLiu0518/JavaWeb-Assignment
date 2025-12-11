package tech.bobliu.assignment07.controller

import com.google.gson.Gson
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import tech.bobliu.assignment07.model.User
import tech.bobliu.assignment07.service.UserService

@WebServlet(name = "userServlet", value = ["/user/*"])
class UserServlet : HttpServlet() {
    private val gson = Gson()

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        when (request.pathInfo) {
            "/online" -> {
                response.contentType = "application/json;charset=UTF-8"
                val user = request.session.getAttribute("user") as User
                val onlineUsers = UserService.getOnlineUsers().map {
                    mapOf(
                        "id" to it.id,
                        "username" to it.username,
                        "isSelf" to (it.id == user.id)
                    )
                }

                response.writer.write(gson.toJson(mapOf("status" to "success", "users" to onlineUsers)))
            }
        }
    }
}