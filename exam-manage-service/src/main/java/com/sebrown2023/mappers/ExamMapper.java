package com.sebrown2023.mappers;

import com.sebrown2023.dto.deprecated.ExamDto;
import com.sebrown2023.dto.deprecated.PostExamDto;
import com.sebrown2023.model.db.Exam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExamMapper {
    ExamDto examToExamDto(Exam exam);
    Exam postExamDtoToExam(PostExamDto postExamDto);
}
