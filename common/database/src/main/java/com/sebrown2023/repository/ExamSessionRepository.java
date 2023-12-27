package com.sebrown2023.repository;

import com.sebrown2023.model.db.ExamSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExamSessionRepository extends JpaRepository<ExamSession, UUID> {
    Optional<ExamSession> findExamSessionById(UUID uuid);

    List<ExamSession> findExamSessionByExam_Id(Long examId);
}
