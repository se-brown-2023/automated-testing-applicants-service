package com.sebrown2023.mappers;

import com.sebrown2023.dto.ExamSessionDto;
import com.sebrown2023.model.db.ExamSession;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExamSessionMapper {

    ExamSessionDto examSessionToExamSessionDto(ExamSession examSession);
}
