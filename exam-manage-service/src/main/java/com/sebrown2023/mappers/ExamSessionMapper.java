package com.sebrown2023.mappers;

import com.sebrown2023.dto.deprecated.ExamSessionDto;
import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.dto.ExamSessionComponent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamSessionMapper {

    @Mapping(target = "id", ignore = true)
    ExamSessionDto examSessionToExamSessionDto(ExamSession examSession);

    @Mapping(target = "id", expression = "java(examSession.getId().toString())")
    ExamSessionComponent examSessionToExamSessionComponent(ExamSession examSession);
}
