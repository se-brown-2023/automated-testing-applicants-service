package com.sebrown2023.mappers;

import com.sebrown2023.model.db.Exam;
import com.sebrown2023.model.dto.ExamComponent;
import com.sebrown2023.model.dto.TaskComponent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Duration;
import java.util.List;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface ExamMapper {

    @Mapping(target = "maxDuration", expression = "java(longToDuration(examComponent.getMaxDuration()))")
    @Mapping(target = "ttl", source = "TTL")
    Exam examComponentToExam(ExamComponent examComponent);

    @Mapping(target = "maxDuration", expression = "java(Long.valueOf(exam.getMaxDuration().getSeconds()).intValue())")
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "TTL", expression = "java(Long.valueOf(exam.getTtl().toSeconds()).intValue())")
    ExamComponent examToExamComponentShort(Exam exam);

    @Mapping(target = "maxDuration", expression = "java(Long.valueOf(exam.getMaxDuration().getSeconds()).intValue())")
    @Mapping(target = "tasks", expression = "java(tasksOfExam)")
    @Mapping(target = "TTL", expression = "java(Long.valueOf(exam.getTtl().toSeconds()).intValue())")
    ExamComponent examToExamComponent(Exam exam, List<TaskComponent> tasksOfExam);

    default Duration longToDuration(Integer duration) {
        return duration == null ? Duration.ZERO : Duration.ofSeconds(duration);
    }
}
