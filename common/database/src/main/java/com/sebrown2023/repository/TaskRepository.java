package com.sebrown2023.repository;

import com.sebrown2023.model.db.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findTaskById(Long id);

    List<Task> findTasksByExamId(Long examId);

    void updateTaskById(Long id, Task task);
}
