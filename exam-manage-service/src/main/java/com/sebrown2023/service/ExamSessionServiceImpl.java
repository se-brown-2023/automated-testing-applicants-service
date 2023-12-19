package com.sebrown2023.service;

import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.db.Status;
import com.sebrown2023.model.exceptions.ExamSessionNotFoundException;
import com.sebrown2023.repository.ExamSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExamSessionServiceImpl implements ExamSessionService {
    @Autowired
    private ExamSessionRepository sessionRepository;

    @Override
    public ExamSession create() {
        ExamSession session = new ExamSession();
        session.setStatus(Status.CREATED);

        return sessionRepository.save(session);
    }

    @Override
    public ExamSession get(UUID uuid) throws ExamSessionNotFoundException {
        return sessionRepository.findById(uuid).orElseThrow(ExamSessionNotFoundException::new);
    }

    @Override
    public void deleteSession(UUID uuid) throws ExamSessionNotFoundException {
        sessionRepository.deleteById(uuid);
    }
}
