package com.sebrown2023.repository;

import com.sebrown2023.model.db.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.Set;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    Exam findExamById(long id);
    Set<Exam> findExamsByExaminerIdEquals(int examinerId);
}
