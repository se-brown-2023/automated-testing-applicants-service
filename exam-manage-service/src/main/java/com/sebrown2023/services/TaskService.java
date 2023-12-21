package com.sebrown2023.services;

import com.sebrown2023.dto.deprecated.PostTaskDto;
import com.sebrown2023.dto.deprecated.TaskDto;
import com.sebrown2023.mappers.TaskMapper;
import com.sebrown2023.mappers.TestMapper;
import com.sebrown2023.repository.TaskRepository;
import com.sebrown2023.repository.TestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TestRepository testRepository;

    private final TaskMapper taskMapper;
    private final TestMapper testMapper;

    public TaskDto getTaskDtoById(long taskId) {
        var task = taskRepository.findTaskById(taskId);
        return taskMapper.taskToTaskDto(task);
    }

    public List<TaskDto> getAllTaskDto() {
        var tests = taskRepository.findAll();
        return tests.stream()
                .map(taskMapper::taskToTaskDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createTask(PostTaskDto postTaskDto) {
        taskRepository.save(taskMapper.postTaskDtoToTask(postTaskDto));

        if (postTaskDto.tests() != null) {
            postTaskDto.tests().stream()
                    .map(testMapper::postTestDtoToTest)
                    .forEach(testRepository::save);
        }
    }

    //TODO подумать что делать со связанными testResult
    @Transactional
    public void deleteTask(Long taskId) {
        testRepository.deleteAll(testRepository.findTestsByTaskId(taskId));
        taskRepository.deleteById(taskId);
    }
}
