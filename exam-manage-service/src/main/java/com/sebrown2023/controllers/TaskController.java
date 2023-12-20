package com.sebrown2023.controllers;

import com.sebrown2023.dto.PostTaskDto;
import com.sebrown2023.dto.TaskDto;
import com.sebrown2023.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    @GetMapping(value = "/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable long taskId) {
        var taskDto = taskService.getTaskDtoById(taskId);
        return ResponseEntity.ok(taskDto);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<TaskDto>> getAllTask() {
        var tasksDto = taskService.getAllTaskDto();
        return ResponseEntity.ok(tasksDto);
    }

    @PostMapping()
    public ResponseEntity<?> createNewTask(PostTaskDto postTaskDto) {
        taskService.createTask(postTaskDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }
}
