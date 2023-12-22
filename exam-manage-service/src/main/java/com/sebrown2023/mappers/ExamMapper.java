package com.sebrown2023.mappers;

import com.sebrown2023.dto.deprecated.ExamDto;
import com.sebrown2023.dto.deprecated.PostExamDto;
import com.sebrown2023.model.db.Exam;
import com.sebrown2023.model.dto.ExamComponent;
import com.sebrown2023.model.dto.TaskComponent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Duration;
import java.util.List;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface ExamMapper {
    ExamDto examToExamDto(Exam exam);

    Exam postExamDtoToExam(PostExamDto postExamDto);

    //////////
    @Mapping(target = "maxDuration", expression = "java(longToDuration(examComponent.getMaxDuration()))")
    Exam examComponentToExam(ExamComponent examComponent);

    @Mapping(target = "maxDuration", expression = "java(new Long(exam.getMaxDuration().getSeconds()).intValue())")
    @Mapping(target = "tasks", ignore = true)
    ExamComponent examToExamComponentShort(Exam exam);

    @Mapping(target = "maxDuration", expression = "java(new Long(exam.getMaxDuration().getSeconds()).intValue())")
    @Mapping(target = "tasks", expression = "java(tasksOfExam)")
    ExamComponent examToExamComponent(Exam exam, List<TaskComponent> tasksOfExam);

    default Duration longToDuration(Integer duration) {
        return duration == null ? Duration.ZERO : Duration.ofSeconds(duration);
    }
}
