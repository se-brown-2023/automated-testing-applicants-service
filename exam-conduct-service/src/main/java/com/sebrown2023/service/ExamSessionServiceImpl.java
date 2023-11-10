package com.sebrown2023.service;

import com.sebrown2023.model.db.Exam;
import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.db.Status;
import com.sebrown2023.model.db.Task;
import com.sebrown2023.model.exceptions.ExamSessionAlreadyFinishedException;
import com.sebrown2023.model.exceptions.ExamSessionException;
import com.sebrown2023.model.exceptions.ExamSessionExpiredException;
import com.sebrown2023.model.exceptions.ExamSessionNotExpiredException;
import com.sebrown2023.model.exceptions.ExamSessionNotFoundException;
import com.sebrown2023.model.exceptions.ExamSessionNotStartedException;
import com.sebrown2023.repository.ExamSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ExamSessionServiceImpl implements ExamSessionService {
    @Autowired
    private ExamSessionRepository sessionRepository;

    @Override
    public ExamSession startSession(UUID uuid) throws ExamSessionException {
        ExamSession session = sessionRepository.findById(uuid).orElseThrow(ExamSessionNotFoundException::new);

        switch (session.getStatus()) {
            case EXPIRED, FINISHED, STARTED -> throw new ExamSessionException("Exam session can not be started");
        }

        boolean isExpired = checkSessionExpiration(session);
        if (isExpired) {
            session.setStatus(Status.EXPIRED);
            sessionRepository.save(session);

            throw new ExamSessionExpiredException();
        }

        LocalDateTime now = LocalDateTime.now();
        session.setStarTimestamp(now);
        session.setStatus(Status.STARTED);

        return sessionRepository.save(session);

    }

    @Override
    public ExamSession finishSession(UUID uuid) throws ExamSessionException {
        ExamSession session = sessionRepository.findById(uuid).orElseThrow(ExamSessionNotFoundException::new);

        switch (session.getStatus()) {
            case EXPIRED -> throw new ExamSessionExpiredException();
            case STARTED -> {
                Duration duration = checkExamExpiration(uuid);
                if (duration.isPositive()) {
                    throw new ExamSessionNotExpiredException();
                } else {
                    session.setStatus(Status.FINISHED);
                }
            }
            case CREATED -> throw new ExamSessionNotStartedException();
            case FINISHED -> throw new ExamSessionAlreadyFinishedException();
        }

        return sessionRepository.save(session);
    }

    /**
     * This method is supposed to check link expiration
     */
    @Override
    public boolean checkSessionExpiration(ExamSession session) {
        return session.getExam().getCreationDate().plus(session.getExam().getTtl()).isBefore(LocalDateTime.now());
    }

    /**
     * This method is supposed to check how much time is left for exam when it's started
     */
    @Override
    public Duration checkExamExpiration(UUID uuid) throws ExamSessionException {
        ExamSession session = sessionRepository.findById(uuid).orElseThrow(ExamSessionNotFoundException::new);

        return switch (session.getStatus()) {
            case EXPIRED -> Duration.ZERO;
            case CREATED, FINISHED -> throw new ExamSessionException("Can not check session expiration");
            case STARTED -> {
                LocalDateTime start = session.getStarTimestamp();
                LocalDateTime now = LocalDateTime.now();

                Duration duration = Duration.between(start, now);

                if (duration.isNegative()) {
                    session.setStatus(Status.EXPIRED);
                    sessionRepository.save(session);
                }

                yield duration;
            }
        };
    }

    @Override
    public List<Task> getExamTasks(UUID uuid) throws ExamSessionException {
        ExamSession session = sessionRepository.findById(uuid).orElseThrow(ExamSessionNotFoundException::new);
        Exam exam = session.getExam();

        boolean isExpired = checkSessionExpiration(session);

        if (isExpired) {
            throw new ExamSessionExpiredException();
        }

        return exam.getTasks();
    }

}
