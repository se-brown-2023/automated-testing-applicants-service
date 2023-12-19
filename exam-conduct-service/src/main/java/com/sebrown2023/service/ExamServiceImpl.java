package com.sebrown2023.service;

import com.sebrown2023.exceptions.ExamNotFoundException;
import com.sebrown2023.model.db.Exam;
import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.db.Status;
import com.sebrown2023.exceptions.ExamSessionAlreadyStartedException;
import com.sebrown2023.exceptions.ExamSessionException;
import com.sebrown2023.exceptions.ExamSessionExpiredException;
import com.sebrown2023.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamSessionService sessionService;
    @Autowired
    private ExamRepository examRepository;

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

    @Override
    public Exam getExamById(Long examId) throws ExamNotFoundException {
        return examRepository.findById(examId).orElseThrow(ExamNotFoundException::new);
    }
}
