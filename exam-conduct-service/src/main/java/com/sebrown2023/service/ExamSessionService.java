package com.sebrown2023.service;

import com.sebrown2023.exceptions.ExamNotFoundException;
import com.sebrown2023.exceptions.ExamSessionException;
import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.db.Task;
import com.sebrown2023.model.dto.Submission;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

public interface ExamSessionService {
    ExamSession create(Long examId) throws ExamNotFoundException;
    Long getAvailableTimeMinutes(UUID uuid);
    ExamSession getByUUID(UUID uuid) throws ExamSessionException;
    void sendTask(UUID uuid, Submission submission) throws ExamSessionException;
    ExamSession startSession(UUID uuid) throws ExamSessionException;
    boolean checkSessionExpiration(ExamSession session);
    Duration checkExamExpiration(ExamSession session) throws ExamSessionException;;
    ExamSession finishSession(UUID uuid) throws ExamSessionException;
    List<Task> getExamTasks(UUID uuid) throws ExamSessionException;
    void deleteSession(UUID uuid);

}
