package com.sebrown2023.service;

import com.sebrown2023.exceptions.ExamNotFoundException;
import com.sebrown2023.exceptions.ExamSessionAlreadyFinishedException;
import com.sebrown2023.exceptions.ExamSessionException;
import com.sebrown2023.exceptions.ExamSessionExpiredException;
import com.sebrown2023.exceptions.ExamSessionNotExpiredException;
import com.sebrown2023.exceptions.ExamSessionNotFoundException;
import com.sebrown2023.exceptions.ExamSessionNotStartedException;
import com.sebrown2023.model.db.Exam;
import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.db.Status;
import com.sebrown2023.model.db.Task;
import com.sebrown2023.model.dto.Submission;
import com.sebrown2023.repository.ExamRepository;
import com.sebrown2023.repository.ExamSessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ExamSessionServiceImpl implements ExamSessionService {
    private final ExamSessionRepository sessionRepository;
    private final ExamRepository examRepository;

    public ExamSessionServiceImpl(ExamSessionRepository sessionRepository, ExamRepository examRepository) {
        this.sessionRepository = sessionRepository;
        this.examRepository = examRepository;
    }

    @Override
    public ExamSession getByUUID(UUID uuid) throws ExamSessionException {
        return sessionRepository.findById(uuid).orElseThrow(ExamSessionNotFoundException::new);
    }

    @Override
    public void sendTask(UUID uuid, Submission submission) throws ExamSessionException {
        ExamSession examSession = getByUUID(uuid);
        checkExamExpiration(examSession);
        if (examSession.getStatus() != Status.STARTED) {
            sessionRepository.save(examSession);
            throw new ExamSessionException(HttpStatus.BAD_REQUEST, "Task cant be sent. ExamSession are not valid now");
        }

        //TODO implement send submission to kafka
    }

    @Override
    public ExamSession startSession(UUID uuid) throws ExamSessionException {
        ExamSession session = getByUUID(uuid);

        switch (session.getStatus()) {
            case EXPIRED, FINISHED, STARTED -> throw new ExamSessionException(HttpStatus.BAD_REQUEST, "Exam session can not be started");
        }

        boolean isExpired = checkSessionExpiration(session);
        if (isExpired) {
            session.setStatus(Status.EXPIRED);
            sessionRepository.save(session);

            throw new ExamSessionExpiredException();
        }

        LocalDateTime now = LocalDateTime.now();
        session.setStartTimestamp(now);
        session.setStatus(Status.STARTED);

        return sessionRepository.save(session);

    }

    @Override
    public ExamSession finishSession(UUID uuid) throws ExamSessionException {
        ExamSession session = getByUUID(uuid);

        switch (session.getStatus()) {
            case EXPIRED -> throw new ExamSessionExpiredException();
            case STARTED -> {
                Duration duration = checkExamExpiration(session);
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
    public Duration checkExamExpiration(ExamSession session) throws ExamSessionException {
        return switch (session.getStatus()) {
            case EXPIRED -> Duration.ZERO;
            case CREATED, FINISHED -> throw new ExamSessionException(HttpStatus.BAD_REQUEST, "Can not check session expiration");
            case STARTED -> {
                LocalDateTime start = session.getStartTimestamp();
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
        ExamSession session = getByUUID(uuid);
        Exam exam = session.getExam();

        boolean isExpired = checkSessionExpiration(session);

        if (isExpired) {
            throw new ExamSessionExpiredException();
        }

        return exam.getTasks();
    }

    @Override
    public ExamSession create(Long examId) throws ExamNotFoundException {
        ExamSession session = new ExamSession();
        Exam exam = examRepository.findById(examId).orElseThrow(ExamNotFoundException::new);
        session.setExam(exam);
        session.setStatus(Status.CREATED);

        return sessionRepository.save(session);
    }

    @Override
    public Long getAvailableTimeMinutes(UUID uuid) {
        ExamSession session = getByUUID(uuid);
        return session.getExam().getMaxDuration().toMinutes();
    }

    @Override
    public void deleteSession(UUID uuid) {
        sessionRepository.deleteById(uuid);
    }

}
