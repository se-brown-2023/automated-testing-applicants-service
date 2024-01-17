package com.sebrown2023.services;

import com.sebrown2023.exceptions.NoElementException;
import com.sebrown2023.mappers.TaskMapper;
import com.sebrown2023.mappers.TestMapper;
import com.sebrown2023.model.db.Task;
import com.sebrown2023.model.dto.TaskComponent;
import com.sebrown2023.repository.ExamRepository;
import com.sebrown2023.repository.TaskRepository;
import com.sebrown2023.repository.TestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TestRepository testRepository;

    private final TaskMapper taskMapper;
    private final TestMapper testMapper;
    private final ExamRepository examRepository;

    public TaskComponent getTaskComponentById(long taskId) {
        var task = taskRepository.findTaskById(taskId);

        if (task.isPresent()) {
            return buildTaskComponent(task.get());
        } else throw new NoElementException("Task with id ");
    }

    public List<TaskComponent> getAllTaskComponents() {
        var tests = taskRepository.findAll();
        return tests.stream()
                .map(this::buildTaskComponent)
                .toList();
    }

    public List<TaskComponent> getAllTaskComponentsByExamId(long examId) {
        var tests = taskRepository.findTasksByExamId(examId);
        return tests.stream()
                .map(this::buildTaskComponent)
                .toList();
    }

    @Transactional
    public TaskComponent createTask(TaskComponent taskComponent) {
        var task = taskMapper.taskComponentToTask(taskComponent);

        if (taskComponent.getExamId() != null) {
            var exam = examRepository.findExamById(taskComponent.getExamId());
            exam.ifPresent(task::setExam);
        }

        var createdTask = taskRepository.save(task);

        taskComponent.getTests().forEach(testComponent -> {
                    var test = testMapper.testComponentToTest(testComponent);
                    test.setTask(createdTask);
                    testRepository.save(test);
                }
        );
        return buildTaskComponent(createdTask);
    }

    @Transactional
    public void deleteTask(Long taskId) {
        testRepository.deleteAll(testRepository.findTestsByTaskId(taskId));
        taskRepository.deleteById(taskId);
    }

    /**
     * Builds a TaskComponent based on the provided Task entity.
     *
     * @param task The Task entity for which the TaskComponent is to be built.
     * @return A TaskComponent instance representing the provided Task entity, including associated test components.
     */
    private TaskComponent buildTaskComponent(Task task) {
        var testsForTask = testRepository.findTestsByTaskId(task.getId());
        var testComponents = testsForTask.stream()
                .map(testMapper::testToTestComponent)
                .toList();
        return taskMapper.taskToTaskComponent(task, testComponents);
    }
}
