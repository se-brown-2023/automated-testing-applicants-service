package com.sebrown2023.model.dto;

import java.util.Date;
import java.util.UUID;

public record SubmissionDto(
        long taskId,
        UUID examSessionId,
        String userSourceCode,
        Date submitTime
) {
    @Override
    public String toString() {
        return "SubmissionDto[" +
                "taskId=" + taskId + ", " +
                "examSessionId=" + examSessionId.toString() + ", " +
                "userSourceCode=" + userSourceCode.replace("\n", "\\n") + ", " +
                "submitTime=" + submitTime + ']';
    }
}
