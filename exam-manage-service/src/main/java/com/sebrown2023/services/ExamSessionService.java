package com.sebrown2023.services;

import com.sebrown2023.exceptions.ExamSessionNotFoundException;
import com.sebrown2023.mappers.ExamSessionMapper;
import com.sebrown2023.model.dto.ExamSessionComponent;
import com.sebrown2023.repository.ExamRepository;
import com.sebrown2023.repository.ExamSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamSessionService {
    private final ExamSessionRepository examSessionRepository;
    private final ExamRepository examRepository;


    private final ExamSessionMapper examSessionMapper;

    public ExamSessionComponent getExamSessionComponentById(String examSessionId) {
        var examSession = examSessionRepository.findExamSessionById(UUID.fromString(examSessionId))
                .orElseThrow(() -> new ExamSessionNotFoundException(examSessionId));

        return examSessionMapper.examSessionToExamSessionComponent(examSession);
    }

    public List<ExamSessionComponent> getAllExamSessions() {
        var tests = examSessionRepository.findAll();
        return tests.stream()
                .map(examSessionMapper::examSessionToExamSessionComponent)
                .toList();
    }

    public List<ExamSessionComponent> getExamSessionComponentsByExamId(long examId) {
        var tests = examSessionRepository.findExamSessionByExam_Id(examId);
        return tests.stream()
                .map(examSessionMapper::examSessionToExamSessionComponent)
                .toList();
    }

    public void deleteExamSession(String examSessionId) {
        var examSession = examSessionRepository.findExamSessionById(UUID.fromString(examSessionId))
                .orElseThrow(() -> new ExamSessionNotFoundException(examSessionId));

        examSessionRepository.delete(examSession);
    }

    public ExamSessionComponent createNewExamSession(ExamSessionComponent examSessionComponent) {
        var exam = examRepository.findExamById(examSessionComponent.getExamId())
                .orElseThrow(() -> new ExamSessionNotFoundException(examSessionComponent.getExamId().toString()));

        var examSession = examSessionMapper.examSessionComponentToExamSession(examSessionComponent, exam);

        var createdExamSession = examSessionRepository.save(examSession);
        return examSessionMapper.examSessionToExamSessionComponent(createdExamSession);
    }
}
