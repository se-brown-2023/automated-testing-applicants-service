package com.sebrown2023.model.dto;

import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.db.Task;

import java.util.Date;

public record SubmissionDto(
        Task task,
        ExamSession examSession,
        String userSourceCode,
        Date submitTime
) {
}
