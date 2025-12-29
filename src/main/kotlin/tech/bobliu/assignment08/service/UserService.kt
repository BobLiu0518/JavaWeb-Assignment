package tech.bobliu.assignment08.service

import tech.bobliu.assignment08.model.User

interface UserService {
    fun register(username: String, password: String): User
    fun login(username: String, password: String): User?
}
