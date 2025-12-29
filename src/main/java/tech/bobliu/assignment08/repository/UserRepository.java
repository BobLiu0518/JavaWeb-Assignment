package tech.bobliu.assignment08.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.bobliu.assignment08.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
