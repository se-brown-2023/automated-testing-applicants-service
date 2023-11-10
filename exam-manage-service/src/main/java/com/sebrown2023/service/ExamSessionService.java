package com.sebrown2023.service;

import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.exceptions.ExamSessionNotFoundException;

import java.util.UUID;

public interface ExamSessionService {
    ExamSession create();
    ExamSession get(UUID uuid) throws ExamSessionNotFoundException;
    void deleteSession(UUID uuid) throws ExamSessionNotFoundException;
}
