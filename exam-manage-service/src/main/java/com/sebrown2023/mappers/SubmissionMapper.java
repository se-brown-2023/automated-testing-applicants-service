package com.sebrown2023.mappers;

import com.sebrown2023.model.db.Submission;
import com.sebrown2023.model.dto.SubmissionComponent;
import com.sebrown2023.model.dto.TestResultComponent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {

    @Mapping(target = "taskId", expression = "java(submission.getTask().getId())")
    @Mapping(target = "examSessionId", expression = "java(submission.getExamSession().getId().toString())")
    @Mapping(target = "testResults", expression = "java(testResults)")
    SubmissionComponent submissionToSubmissionComponent(Submission submission, List<TestResultComponent> testResults);
}
