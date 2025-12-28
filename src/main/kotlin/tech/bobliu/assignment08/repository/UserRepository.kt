package tech.bobliu.assignment08.repository

import org.springframework.data.jpa.repository.JpaRepository
import tech.bobliu.assignment08.model.User

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}
