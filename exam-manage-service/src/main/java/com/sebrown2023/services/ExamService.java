package com.sebrown2023.services;

import com.sebrown2023.dto.deprecated.ExamDto;
import com.sebrown2023.dto.deprecated.PostExamDto;
import com.sebrown2023.dto.deprecated.TaskDto;
import com.sebrown2023.dto.deprecated.TestDto;
import com.sebrown2023.mappers.ExamMapper;
import com.sebrown2023.mappers.TaskMapper;
import com.sebrown2023.mappers.TestMapper;
import com.sebrown2023.model.db.Exam;
import com.sebrown2023.repository.ExamRepository;
import com.sebrown2023.repository.TaskRepository;
import com.sebrown2023.repository.TestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ExamDto getExamDtoById(long examId) {
        var exam = examRepository.findExamById(examId);
        return examMapper.examToExamDto(exam);
    }

    public List<ExamDto> getAllExamDtoByExamineeId(int examineeId) {
        var exams = examRepository.findExamsByExaminerIdEquals(examineeId);
        return exams.stream()
                .map(this::buildExamDto)
                .collect(Collectors.toList());
    }

    public List<ExamDto> getAllExamDto() {
        var exams = examRepository.findAll();
        return exams.stream()
                .map(examMapper::examToExamDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createExam(PostExamDto postExamDto) {
        if (postExamDto == null) {
            throw new RuntimeException("Empty Dto");
        }

        //TODO нижу тоже можно доавбить проверок, но пока не понятно какой будет контракт
        var createdExam = examRepository.save(examMapper.postExamDtoToExam(postExamDto));

        postExamDto.tasks().forEach( taskDto -> {

                    var tests = taskDto.tests();
                    var task = taskMapper.postTaskDtoToTask(taskDto);
                    task.setExam(createdExam);

                    var createdTask = taskRepository.save(task);

                    tests.forEach( testDto -> {
                            var test = testMapper.postTestDtoToTest(testDto);
                            test.setTask(createdTask);

                            testRepository.save(test);
                            }
                    );
                });
    }

    //TODO подумать что делать со связанными testResult и другими подвтсающими сущностями
    @Transactional
    public void deleteExam(Long examId) {
        //TODO в репозиториях стоит возвращать Optional для проверки на null
        var tasks = taskRepository.findTasksByExamId(examId);

        tasks.forEach(task -> {
            testRepository.deleteAll(testRepository.findTestsByTaskId(task.getId()));
            taskRepository.delete(task);
        });

        examRepository.deleteById(examId);
    }

    //Todo вынести в маппер
    private ExamDto buildExamDto(Exam exam) {
        var examDto = examMapper.examToExamDto(exam);
        List<TaskDto> tasksForExam = new ArrayList<>();

        taskRepository.findTasksByExamId(exam.getId()).forEach( task -> {
            List<TestDto> testsForTask = new ArrayList<>();

            testRepository.findTestsByTaskId(task.getId()).forEach( test -> {
                testsForTask.add(testMapper.testToTestDto(test));
            });

//            var taskDto = taskMapper.taskToTaskDto(task, testsForTask);
            //Todo разобраться с dto классами
        });
        return examDto;
    }
}
