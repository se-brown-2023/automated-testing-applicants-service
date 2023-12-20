package com.sebrown2023.dto;

import com.sebrown2023.model.db.Exam;
import com.sebrown2023.model.db.Examinee;
import com.sebrown2023.model.db.Status;
import jakarta.persistence.*;

import java.util.Date;

public record ExamSessionDto (
    Long id,
    Long examId,
    Long examineId,
    Status status,
    Date starTimestamp,
    Date finishTimestamp,
    Boolean expired
) {}
