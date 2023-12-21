package com.sebrown2023.repository;

import com.sebrown2023.model.db.Submission;
import com.sebrown2023.model.db.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findTestResultBySubmission(Submission submission);
}
