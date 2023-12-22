package com.sebrown2023.repository;

import com.sebrown2023.model.db.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Submission findSubmissionById(Long id);
}
