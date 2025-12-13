package tech.bobliu.assignment07.controller

import com.google.gson.Gson
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import tech.bobliu.assignment07.service.UserService

@WebServlet(name = "authServlet", value = ["/auth/*"])
class AuthServlet : HttpServlet() {
    private val gson = Gson()

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        when (request.pathInfo) {
            "/login" -> {
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response)
            }

            "/logout" -> {
                response.sendRedirect("${request.contextPath}/auth/login")
            }
        }
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "application/json;charset=UTF-8"

        when (request.pathInfo) {
            "/login" -> runCatching {
                val body = gson.fromJson(request.reader, Map::class.java)
                val username = body["username"] as String?

                require(!username.isNullOrEmpty()) { "用户名为空" }
                require(username.length >= 4) { "用户名至少 4 位" }

                val user = UserService.loginAsUser(username)
                request.session.apply {
                    setAttribute("user", user)
                    maxInactiveInterval = 60
                }

                "登录成功"
            }.onSuccess { msg ->
                response.writer.write(gson.toJson(mapOf("status" to "success", "message" to msg)))
            }.onFailure { e ->
                response.status = 400
                response.writer.write(gson.toJson(mapOf("status" to "error", "message" to e.message)))
            }
        }
    }
}