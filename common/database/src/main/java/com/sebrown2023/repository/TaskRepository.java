package com.sebrown2023.repository;

import com.sebrown2023.model.db.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
