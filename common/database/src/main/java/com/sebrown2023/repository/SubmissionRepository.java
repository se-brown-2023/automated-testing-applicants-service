package com.sebrown2023.repository;

import com.sebrown2023.model.db.Submission;
import org.springframework.data.repository.CrudRepository;

public interface SubmissionRepository extends CrudRepository<Submission, Long> {
}
