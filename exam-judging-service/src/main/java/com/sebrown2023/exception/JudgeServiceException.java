package com.sebrown2023.exception;

public sealed class JudgeServiceException extends RuntimeException {
    public JudgeServiceException(String message) {
        super(message);
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
}
