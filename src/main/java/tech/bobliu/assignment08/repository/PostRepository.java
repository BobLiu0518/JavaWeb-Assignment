package tech.bobliu.assignment08.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.bobliu.assignment08.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
