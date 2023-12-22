package com.sebrown2023.repository;

import com.sebrown2023.model.db.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Test findTestById(long id);

    List<Test> findTestsByTaskId(Long task_id);

    void deleteTestById(Long id);
}
