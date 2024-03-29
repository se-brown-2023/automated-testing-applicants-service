package com.sebrown2023.repository;

import com.sebrown2023.model.db.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    Optional<Exam> findExamById(long id);

    Set<Exam> findExamsByExaminerIdEquals(long examinerId);
}
