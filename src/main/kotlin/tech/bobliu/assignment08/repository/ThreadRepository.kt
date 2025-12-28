package tech.bobliu.assignment08.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import tech.bobliu.assignment08.model.Thread

interface ThreadRepository : JpaRepository<Thread, Long> {
    @EntityGraph(attributePaths = ["sender"])
    override fun findAll(): List<Thread>
}
