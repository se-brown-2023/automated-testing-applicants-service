package com.sebrown2023.repository;

import com.sebrown2023.model.db.ExamSession;
import org.springframework.data.repository.CrudRepository;

public interface ExamSessionRepository extends CrudRepository<ExamSession, Long> {
}
