package com.sebrown2023.service;

import com.sebrown2023.model.db.Exam;
import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.db.Status;
import com.sebrown2023.model.exceptions.ExamSessionAlreadyStartedException;
import com.sebrown2023.model.exceptions.ExamSessionException;
import com.sebrown2023.model.exceptions.ExamSessionExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamSessionService sessionService;

    @Override
    public Exam getExamByUUID(UUID uuid) throws ExamSessionException {
        ExamSession session = sessionService.getByUUID(uuid);

        if (session.getStatus() == Status.EXPIRED) {
            throw new ExamSessionExpiredException();
        }

        if (session.getStatus() == Status.STARTED) {
            throw new ExamSessionAlreadyStartedException();
        }

        return session.getExam();
    }
}
