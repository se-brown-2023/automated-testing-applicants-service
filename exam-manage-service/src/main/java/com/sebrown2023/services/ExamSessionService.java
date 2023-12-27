package com.sebrown2023.services;

import com.sebrown2023.exceptions.NoElementException;
import com.sebrown2023.mappers.ExamSessionMapper;
import com.sebrown2023.model.dto.ExamSessionComponent;
import com.sebrown2023.repository.ExamSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamSessionService {
    private final ExamSessionRepository examSessionRepository;

    private final ExamSessionMapper examSessionMapper;

    public ExamSessionComponent getExamSessionComponentById(String examSessionId) {
        var examSession = examSessionRepository.findExamSessionById(UUID.fromString(examSessionId));

        if (examSession.isPresent()) {
            return examSessionMapper.examSessionToExamSessionComponent(examSession.get());
        } else throw new NoElementException();
    }

    public List<ExamSessionComponent> getAllExamSessions() {
        var tests = examSessionRepository.findAll();
        return tests.stream()
                .map(examSessionMapper::examSessionToExamSessionComponent)
                .collect(Collectors.toList());
    }

    public List<ExamSessionComponent> getExamSessionComponentsByExamId(long examId) {
        var tests = examSessionRepository.findExamSessionByExam_Id(examId);
        return tests.stream()
                .map(examSessionMapper::examSessionToExamSessionComponent)
                .collect(Collectors.toList());
    }

    public void deleteExamSession(String examSessionId) {
        var examSession = examSessionRepository.findExamSessionById(UUID.fromString(examSessionId));

        if (examSession.isPresent()) {
            examSessionRepository.delete(examSession.get());
        } else throw new NoElementException();
    }
}
