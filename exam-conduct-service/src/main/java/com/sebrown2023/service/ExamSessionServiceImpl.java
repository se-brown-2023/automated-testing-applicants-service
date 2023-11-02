package com.sebrown2023.service;

import com.sebrown2023.model.db.Exam;
import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.db.Link;
import com.sebrown2023.model.db.Status;
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
    public ExamSession startSession(UUID uuid) {
        ExamSession session = sessionRepository.findById(uuid).get();

        if (session.getStatus() == Status.EXPIRED || session.getStatus() == Status.FINISHED
                || session.getStatus() == Status.STARTED) {
            return null;
        }

        Link link = session.getLink();
        if (link.isExpired()) {
            return null;
        } else {
            boolean expired = linkService.checkExpired(link);
            if (expired) {
                return null;
            }
        }

        LocalDateTime now = LocalDateTime.now();
        session.setStarTimestamp(now);
        session.setFinishTimestamp(now.plus(session.getExam().getMaxDuration()));
        session.setStatus(Status.STARTED);

        return sessionRepository.save(session);

    }

    @Override
    public Duration checkSessionExpiration(UUID uuid) {
        ExamSession session = sessionRepository.findById(uuid).get();

        return switch (session.getStatus()) {
            case EXPIRED -> Duration.ZERO;
            case CREATED, FINISHED -> null;
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
