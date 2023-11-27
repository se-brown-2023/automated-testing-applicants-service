package com.sebrown2023.repository;

import com.sebrown2023.model.db.ExamSession;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ExamSessionRepository extends CrudRepository<ExamSession, UUID> {
}
