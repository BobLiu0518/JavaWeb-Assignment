package tech.bobliu.assignment08.repository

import org.springframework.data.jpa.repository.JpaRepository
import tech.bobliu.assignment08.model.Post

interface PostRepository : JpaRepository<Post, Long>
