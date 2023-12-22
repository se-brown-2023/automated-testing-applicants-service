package com.sebrown2023.mappers;

import com.sebrown2023.dto.deprecated.PostTaskDto;
import com.sebrown2023.dto.deprecated.PostTestDto;
import com.sebrown2023.dto.deprecated.TaskDto;
import com.sebrown2023.model.db.Task;
import com.sebrown2023.model.dto.TaskComponent;
import com.sebrown2023.model.dto.TestComponent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto taskToTaskDto(Task task);
    Task postTaskDtoToTask(PostTaskDto postTaskDto);
    Task postTaskDtoToTask(PostTaskDto postTaskDto, List<PostTestDto> testsDto);
    //
    Task taskComponentToTask(TaskComponent taskComponent);

    @Mapping(target = "tests", ignore = true)
    TaskComponent taskToTaskComponentShort(Task task);

    @Mapping(target = "tests", expression = "java(testsOfTask)")
    TaskComponent taskToTaskComponent(Task task, List<TestComponent> testsOfTask);
}
