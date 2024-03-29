package com.sebrown2023.repository;

import com.sebrown2023.model.db.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Optional<Test> findTestById(long id);

    List<Test> findTestsByTaskId(Long task_id);

    void deleteTestById(Long id);

    List<Test> findAllByTaskId(long taskId);
}
