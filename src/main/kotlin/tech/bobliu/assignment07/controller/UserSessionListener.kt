package tech.bobliu.assignment07.controller

import jakarta.servlet.annotation.WebListener
import jakarta.servlet.http.HttpSessionEvent
import jakarta.servlet.http.HttpSessionListener
import tech.bobliu.assignment07.model.User
import tech.bobliu.assignment07.service.UserService

@WebListener
class UserSessionListener : HttpSessionListener {
    override fun sessionDestroyed(se: HttpSessionEvent) {
        (se.session.getAttribute("user") as? User)?.let { user ->
            UserService.setUserAsOffline(user)
        }
    }
}