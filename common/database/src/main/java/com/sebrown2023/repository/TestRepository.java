package com.sebrown2023.repository;

import com.sebrown2023.model.db.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestRepository extends CrudRepository<Test, Long> {
    List<Test> findAllByTaskId(long taskId);
}
