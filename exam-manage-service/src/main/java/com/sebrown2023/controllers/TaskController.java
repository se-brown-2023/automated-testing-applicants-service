package com.sebrown2023.controllers;

import com.sebrown2023.controller.TaskApi;
import com.sebrown2023.model.dto.TaskComponent;
import com.sebrown2023.services.TaskService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController implements TaskApi {
    private final TaskService taskService;

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<TaskComponent> getTask(Long taskId) {
        var taskComponent = taskService.getTaskComponentById(taskId);
        return ResponseEntity.ok(taskComponent);
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<List<TaskComponent>> getAllTasks() {
        var taskComponents = taskService.getAllTaskComponents();
        return ResponseEntity.ok(taskComponents);
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<List<TaskComponent>> getTasksForExam(Long examId) {
        var taskComponents = taskService.getAllTaskComponentsByExamId(examId);
        return ResponseEntity.ok(taskComponents);
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<TaskComponent> createTask(TaskComponent taskComponent) {
        var createdTaskComponent = taskService.createTask(taskComponent);
        return ResponseEntity.ok().body(createdTaskComponent);
    }

    @Override
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<Void> deleteTask(Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }
}
