package com.sebrown2023.services;

import com.sebrown2023.exceptions.NoElementException;
import com.sebrown2023.mappers.SubmissionMapper;
import com.sebrown2023.mappers.TestResultMapper;
import com.sebrown2023.model.dto.SubmissionComponent;
import com.sebrown2023.repository.SubmissionRepository;
import com.sebrown2023.repository.TestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final TestResultRepository testResultRepository;

    private final TestResultMapper testResultMapper;
    private final SubmissionMapper submissionMapper;

    public SubmissionComponent getSubmissionComponentById(Long submissionId) {
        var submission = submissionRepository.findSubmissionById(submissionId);

        if (submission.isPresent()) {
            var testResults = testResultRepository.findTestResultBySubmission(submission.get()).stream()
                    .map(testResultMapper::testResultToTestResultComponent)
                    .toList();

            return submissionMapper.submissionToSubmissionComponent(submission.get(), testResults);
        } else throw new NoElementException("Submission with id " + submissionId);
    }

    public List<SubmissionComponent> getSubmissionsByExamSessionId(String examSessionId) {
        return submissionRepository.findSubmissionByExamSession_Id(UUID.fromString(examSessionId)).stream()
                .map(submission -> {
                    var testResults = testResultRepository.findTestResultBySubmission(submission).stream()
                            .map(testResultMapper::testResultToTestResultComponent)
                            .toList();

                    return submissionMapper.submissionToSubmissionComponent(submission, testResults);
                })
                .toList();
    }
}
