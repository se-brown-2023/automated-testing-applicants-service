package com.sebrown2023.mappers;

import com.sebrown2023.model.db.Exam;
import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.dto.ExamSessionComponent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring",
        imports = {UUID.class, LocalDateTime.class}, uses = ExamineeMapper.class)
public interface ExamSessionMapper {

    @Mapping(target = "id", expression = "java(examSession.getId().toString())")
    @Mapping(target = "examId", expression = "java(examSession.getExam().getId())")
    @Mapping(target = "startTimeStamp", source = "startTimestamp")
    @Mapping(target = "finishTimeStamp", source = "finishTimestamp")
    ExamSessionComponent examSessionToExamSessionComponent(ExamSession examSession);

    @Mapping(target = "id", expression = "java(examSessionComponent.getId() != null ? UUID.fromString(examSessionComponent.getId()) : null)")
    @Mapping(target = "exam", expression = "java(exam)")
    @Mapping(target = "startTimestamp", expression = "java(examSessionComponent.getStartTimeStamp() != null ? LocalDateTime.parse(examSessionComponent.getStartTimeStamp(), DateTimeFormatter.ISO_LOCAL_DATE_TIME): null)")
    @Mapping(target = "finishTimestamp", expression = "java(examSessionComponent.getFinishTimeStamp() != null ? LocalDateTime.parse(examSessionComponent.getFinishTimeStamp(), DateTimeFormatter.ISO_LOCAL_DATE_TIME): null)")
    ExamSession examSessionComponentToExamSession(ExamSessionComponent examSessionComponent, Exam exam);
}
