package tech.bobliu.assignment08.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import tech.bobliu.assignment08.model.Thread;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
    @Override
    @EntityGraph(attributePaths = {"sender"})
    List<Thread> findAll();
}
