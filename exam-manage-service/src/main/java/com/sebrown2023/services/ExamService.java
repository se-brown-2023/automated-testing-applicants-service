package com.sebrown2023.services;

import com.sebrown2023.exceptions.NoElementException;
import com.sebrown2023.mappers.ExamMapper;
import com.sebrown2023.mappers.TaskMapper;
import com.sebrown2023.mappers.TestMapper;
import com.sebrown2023.model.db.Exam;
import com.sebrown2023.model.dto.ExamComponent;
import com.sebrown2023.repository.ExamRepository;
import com.sebrown2023.repository.TaskRepository;
import com.sebrown2023.repository.TestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepository examRepository;
    private final TaskRepository taskRepository;
    private final TestRepository testRepository;

    private final ExamMapper examMapper;
    private final TaskMapper taskMapper;
    private final TestMapper testMapper;

    public ExamComponent getExamComponentById(long examId) {
        var exam = examRepository.findExamById(examId);

        if (exam.isPresent()) {
            return buildExamComponent(exam.get());
        } else throw new NoElementException();
    }

    public List<ExamComponent> getAllExamWithTasksAndTestsByExaminerId(long examinerId) {
        var exams = examRepository.findExamsByExaminerIdEquals(examinerId);
        return exams.stream()
                .map(this::buildExamComponent)
                .collect(Collectors.toList());
    }

    public List<ExamComponent> getAllExamWithTasksAndTests() {
        var exams = examRepository.findAll();
        return exams.stream()
                .map(this::buildExamComponent)
                .collect(Collectors.toList());
    }

    @Transactional
    public ExamComponent createExam(ExamComponent examComponent) {
        var createdExam = examRepository.save(examMapper.examComponentToExam(examComponent));

        examComponent.getTasks().forEach(taskComponent -> {

            var tests = taskComponent.getTests();
            var task = taskMapper.taskComponentToTask(taskComponent);
            task.setExam(createdExam);

            var createdTask = taskRepository.save(task);

            tests.forEach(testComponent -> {
                        var test = testMapper.testComponentToTest(testComponent);
                        test.setTask(createdTask);

                        testRepository.save(test);
                    }
            );
        });
        return buildExamComponent(createdExam);
    }

    @Transactional
    public void deleteExam(Long examId) {
        var tasks = taskRepository.findTasksByExamId(examId);

        tasks.forEach(task -> {
            testRepository.deleteAll(testRepository.findTestsByTaskId(task.getId()));
            taskRepository.delete(task);
        });

        examRepository.deleteById(examId);
    }

    /**
     * Builds an ExamComponent based on the provided Exam entity.
     *
     * @param exam The Exam entity for which the ExamComponent is to be built.
     * @return An ExamComponent representing the provided Exam entity, including associated tasks and tests.
     */
    private ExamComponent buildExamComponent(Exam exam) {
        var tasksForExam = taskRepository.findTasksByExamId(exam.getId());

        var taskComponents = tasksForExam.stream()
                .map(task -> {
                    var testsForTask = testRepository.findTestsByTaskId(task.getId());
                    var testComponents = testsForTask.stream()
                            .map(testMapper::testToTestComponent)
                            .collect(Collectors.toList());
                    return taskMapper.taskToTaskComponent(task, testComponents);
                })
                .toList();
        return examMapper.examToExamComponent(exam, taskComponents);
    }
}
