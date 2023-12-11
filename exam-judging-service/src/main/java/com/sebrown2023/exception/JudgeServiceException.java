package com.sebrown2023.exception;

public sealed class JudgeServiceException extends RuntimeException {
    public JudgeServiceException(String message) {
        super(message);
    }

    public JudgeServiceException(Throwable cause) {
        super(cause);
    }

    public static final class TaskNotFoundException extends JudgeServiceException {
        public TaskNotFoundException(long id) {
            super(STR. "Task with id \{ id } was not found" );
        }
    }

    public static final class ExamSessionNotFoundException extends JudgeServiceException {
        public ExamSessionNotFoundException(long id) {
            super(STR. "Exam session with id \{ id } was not found" );
        }
    }

    public static final class SubmissionProcessingException extends JudgeServiceException {
        public SubmissionProcessingException(Throwable cause) {
            super(cause);
        }
    }
}
