package com.sebrown2023.service;

import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.exceptions.ExamSessionException;
import com.sebrown2023.model.exceptions.ExamSessionNotStartedException;

import java.time.Duration;
import java.util.UUID;

public interface ExamSessionService {
    ExamSession startSession(UUID uuid) throws ExamSessionException;
    Duration checkSessionExpiration(UUID uuid) throws ExamSessionException;
    ExamSession finishSession(UUID uuid) throws ExamSessionException;

}
