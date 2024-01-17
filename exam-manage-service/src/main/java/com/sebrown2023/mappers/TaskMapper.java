package com.sebrown2023.mappers;

import com.sebrown2023.model.db.Task;
import com.sebrown2023.model.dto.TaskComponent;
import com.sebrown2023.model.dto.TestComponent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "exam", ignore = true)
    Task taskComponentToTask(TaskComponent taskComponent);

    @Mapping(target = "tests", ignore = true)
    @Mapping(target = "examId", expression =  "java(task.getExam() != null ? task.getExam().getId() : null)")
    TaskComponent taskToTaskComponentShort(Task task);

    @Mapping(target = "tests", expression = "java(testsOfTask)")
    @Mapping(target = "examId", expression = "java(task.getExam() != null ? task.getExam().getId() : null)")
    TaskComponent taskToTaskComponent(Task task, List<TestComponent> testsOfTask);
}
