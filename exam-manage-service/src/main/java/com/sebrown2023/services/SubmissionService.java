package com.sebrown2023.services;

import com.sebrown2023.dto.GetSubmissionDto;
import com.sebrown2023.dto.TestDto;
import com.sebrown2023.dto.TestResultDto;
import com.sebrown2023.mappers.ExamSessionMapper;
import com.sebrown2023.mappers.TaskMapper;
import com.sebrown2023.mappers.TestMapper;
import com.sebrown2023.mappers.TestResultMapper;
import com.sebrown2023.model.db.Submission;
import com.sebrown2023.repository.SubmissionRepository;
import com.sebrown2023.repository.TestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final TestResultRepository testResultRepository;

    private final TaskMapper taskMapper;
    private final TestMapper testMapper;
    private final TestResultMapper testResultMapper;
    private final ExamSessionMapper examSessionMapper;

    public GetSubmissionDto getSubmissionDtoById (Long submissionId) {
        var submission = submissionRepository.findSubmissionById(submissionId);

        return createSubmissionDto(submission);
    }

    public List<GetSubmissionDto> getAllSubmissionsDto () {
        return submissionRepository.findAll().stream()
                .map(this::createSubmissionDto)
                .collect(Collectors.toList());
    }

    private GetSubmissionDto createSubmissionDto(Submission submission) {
        var testWithTestResults = new LinkedHashMap<TestDto, TestResultDto>();

        testResultRepository.findTestResultBySubmission(submission).forEach(testResult -> {
            testWithTestResults.put(
                    testMapper.testToTestDto(testResult.getTest()),
                    testResultMapper.testResultToTestResultDto(testResult)
            );
        });

        return new GetSubmissionDto(
                taskMapper.taskToTaskDto(submission.getTask()),
                testWithTestResults,
                examSessionMapper.examSessionToExamSessionDto(submission.getExamSession()),
                submission.getUserSourceCode(),
                submission.getSubmitTime()
        );
    }
}
