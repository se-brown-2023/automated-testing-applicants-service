package com.sebrown2023.repository;

import com.sebrown2023.model.db.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findSubmissionById(Long id);

    List<Submission> findSubmissionByExamSession_Id(UUID examSessionId);
}
