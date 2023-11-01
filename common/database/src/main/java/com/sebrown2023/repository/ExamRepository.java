package com.sebrown2023.repository;

import com.sebrown2023.model.db.Exam;
import org.springframework.data.repository.CrudRepository;

public interface ExamRepository extends CrudRepository<Exam, Long> {
}
