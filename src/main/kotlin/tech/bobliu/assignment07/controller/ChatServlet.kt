package tech.bobliu.assignment07.controller

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebServlet(name = "chatServlet", value = ["/chat"])
class ChatServlet : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        request.getRequestDispatcher("/WEB-INF/chat.jsp").forward(request, response)
    }
}