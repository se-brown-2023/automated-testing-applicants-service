package com.sebrown2023.service;

import com.sebrown2023.model.db.Exam;
import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.db.Task;
import com.sebrown2023.model.exceptions.ExamSessionException;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

public interface ExamSessionService {
    ExamSession startSession(UUID uuid) throws ExamSessionException;
    boolean checkSessionExpiration(UUID uuid);
    Duration checkExamExpiration(UUID uuid) throws ExamSessionException;;
    ExamSession finishSession(UUID uuid) throws ExamSessionException;
    public List<Task> getExamTasks(UUID uuid) throws ExamSessionException;

}
