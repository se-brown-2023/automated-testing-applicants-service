package com.sebrown2023.service;

import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.db.Link;
import com.sebrown2023.model.db.Status;
import com.sebrown2023.model.exceptions.ExamSessionAlreadyFinishedException;
import com.sebrown2023.model.exceptions.ExamSessionException;
import com.sebrown2023.model.exceptions.ExamSessionExpiredException;
import com.sebrown2023.model.exceptions.ExamSessionNotExpiredException;
import com.sebrown2023.model.exceptions.ExamSessionNotStartedException;
import com.sebrown2023.repository.ExamSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ExamSessionServiceImpl implements ExamSessionService {
    @Autowired
    private LinkService linkService;

    @Autowired
    private ExamSessionRepository sessionRepository;

    @Override
    public ExamSession startSession(UUID uuid) throws ExamSessionException {
        ExamSession session = sessionRepository.findById(uuid).get();

        switch (session.getStatus()) {
            case EXPIRED, FINISHED, STARTED -> throw new ExamSessionException("Exam session can not be started");
        }

        Link link = session.getLink();
        if (link.isExpired()) {
            throw new ExamSessionExpiredException();
        } else {
            boolean expired = linkService.checkExpired(link);
            if (expired) {
                throw new ExamSessionExpiredException();
            }
        }

        LocalDateTime now = LocalDateTime.now();
        session.setStarTimestamp(now);
        session.setFinishTimestamp(now.plus(session.getExam().getMaxDuration()));
        session.setStatus(Status.STARTED);

        return sessionRepository.save(session);

    }

    @Override
    public ExamSession finishSession(UUID uuid) throws ExamSessionException {
        ExamSession session = sessionRepository.findById(uuid).get();

        switch (session.getStatus()) {
            case EXPIRED -> session.setStatus(Status.FINISHED);
            case STARTED -> {
                Duration duration = checkSessionExpiration(uuid);
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

    @Override
    public Duration checkSessionExpiration(UUID uuid) throws ExamSessionException {
        ExamSession session = sessionRepository.findById(uuid).get();

        return switch (session.getStatus()) {
            case EXPIRED -> Duration.ZERO;
            case CREATED, FINISHED -> throw new ExamSessionException("Can not check session expiration");
            case STARTED -> {
                LocalDateTime start = session.getStarTimestamp();
                LocalDateTime finish = session.getFinishTimestamp();

                Duration duration = Duration.between(start, finish);

                if (duration.isNegative()) {
                    session.setStatus(Status.EXPIRED);
                    sessionRepository.save(session);
                }

                yield duration.isPositive() ? duration : Duration.ZERO;
            }
        };
    }
}
