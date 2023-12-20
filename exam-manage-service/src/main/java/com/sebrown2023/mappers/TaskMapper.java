package com.sebrown2023.mappers;

import com.sebrown2023.dto.PostTaskDto;
import com.sebrown2023.dto.PostTestDto;
import com.sebrown2023.dto.TaskDto;
import com.sebrown2023.model.db.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto taskToTaskDto(Task task);
    Task postTaskDtoToTask(PostTaskDto postTaskDto);
    Task postTaskDtoToTask(PostTaskDto postTaskDto, List<PostTestDto> testsDto);
}