package com.sebrown2023.mappers;

import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.dto.ExamSessionComponent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamSessionMapper {

    @Mapping(target = "id", expression = "java(examSession.getId().toString())")
    @Mapping(target = "examId", expression = "java(examSession.getExam().getId())")
    @Mapping(target = "startTimeStamp", source = "startTimestamp")
    @Mapping(target = "finishTimeStamp", source = "finishTimestamp")
    ExamSessionComponent examSessionToExamSessionComponent(ExamSession examSession);
}
