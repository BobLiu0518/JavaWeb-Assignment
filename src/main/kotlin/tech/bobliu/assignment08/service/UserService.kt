package tech.bobliu.assignment08.service

import org.springframework.stereotype.Service
import tech.bobliu.assignment08.model.User
import tech.bobliu.assignment08.repository.UserRepository
import java.security.MessageDigest
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun register(username: String, password: String): User {
        if (userRepository.findByUsername(username) != null) {
            throw IllegalArgumentException("用户名已存在")
        }
        val user = User(username = username, passwordHash = hashPassword(password))
        return userRepository.save(user)
    }

    fun login(username: String, password: String): User? {
        val user = userRepository.findByUsername(username) ?: return null
        if (user.passwordHash == hashPassword(password)) {
            return user
        }
        return null
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(password.toByteArray())
        return Base64.getEncoder().encodeToString(hash)
    }
}
