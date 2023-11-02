package com.sebrown2023.service;

import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.db.Link;

import java.time.Duration;
import java.util.UUID;

public interface ExamSessionService {
    ExamSession startSession(UUID uuid);
    Duration checkSessionExpiration(UUID uuid);

}
