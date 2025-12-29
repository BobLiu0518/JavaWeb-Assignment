package tech.bobliu.assignment08.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import tech.bobliu.assignment08.model.User
import tech.bobliu.assignment08.repository.UserRepository
import java.util.*

@Service
class UserServiceImpl @Autowired constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun register(username: String, password: String): User {
        if (userRepository.findByUsername(username) != null) {
            throw IllegalArgumentException("用户名已存在")
        }
        val user = User(username = username, passwordHash = passwordEncoder.encode(password)!!)
        return userRepository.save(user)
    }

    override fun login(username: String, password: String): User? {
        val user = userRepository.findByUsername(username) ?: return null
        if (passwordEncoder.matches(password, user.passwordHash)) {
            return user
        }
        return null
    }
}
