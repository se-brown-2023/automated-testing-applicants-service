package com.sebrown2023.model.dto;

import com.sebrown2023.model.db.Exam;
import com.sebrown2023.model.db.Examinee;
import com.sebrown2023.model.db.Status;

import java.util.Date;

public record ExamSessionDto(
        Exam exam,
        Examinee examine,
        Status status,
        Date starTimestamp,
        Date finishTimestamp,
        Boolean expired
) {
}
