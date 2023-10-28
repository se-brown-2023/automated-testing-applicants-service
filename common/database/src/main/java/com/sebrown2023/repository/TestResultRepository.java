package com.sebrown2023.repository;

import com.sebrown2023.model.db.TestResult;
import org.springframework.data.repository.CrudRepository;

public interface TestResultRepository extends CrudRepository<TestResult, Long> {
}
