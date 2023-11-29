package com.sebrown2023.model.dto;

import java.util.Date;

public record SubmissionDto(
        long taskId,
        long examSessionId,
        String userSourceCode,
        Date submitTime
) {
    @Override
    public String toString() {
        return "SubmissionDto[" +
                "taskId=" + taskId + ", " +
                "examSessionId=" + examSessionId + ", " +
                "userSourceCode=" + userSourceCode.replace("\n", "\\n") + ", " +
                "submitTime=" + submitTime + ']';
    }
}
