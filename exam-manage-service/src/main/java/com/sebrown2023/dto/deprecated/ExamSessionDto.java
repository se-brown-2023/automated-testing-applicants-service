package com.sebrown2023.dto.deprecated;

import com.sebrown2023.model.db.Status;

import java.util.Date;

@Deprecated
public record ExamSessionDto (
    Long id,
    Long examId,
    Long examineId,
    Status status,
    Date starTimestamp,
    Date finishTimestamp,
    Boolean expired
) {}
