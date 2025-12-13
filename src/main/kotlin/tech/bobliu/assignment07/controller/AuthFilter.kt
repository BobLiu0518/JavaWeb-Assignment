package tech.bobliu.assignment07.controller

import jakarta.servlet.FilterChain
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import tech.bobliu.assignment07.model.User
import tech.bobliu.assignment07.service.UserService

@WebFilter(filterName = "00 - authFilter", value = ["/*"])
class AuthFilter : HttpFilter() {
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val user = request.session.getAttribute("user") as? User
        if (user == null && request.servletPath in listOf("/chat", "/message")) {
            response.sendRedirect("${request.contextPath}/auth/login")
            return
        }
        if (user != null && request.servletPath == "/auth") {
            UserService.setUserAsOffline(user)
            request.session.removeAttribute("user")
        }
        chain.doFilter(request, response)
    }
}