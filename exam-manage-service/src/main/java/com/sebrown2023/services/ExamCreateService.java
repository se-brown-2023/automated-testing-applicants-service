package com.sebrown2023.services;

import com.sebrown2023.mappers.ExamComponentToExamMapper;
import com.sebrown2023.mappers.TaskComponentToTaskMapper;
import com.sebrown2023.model.db.Exam;
import com.sebrown2023.model.db.Task;
import com.sebrown2023.repository.ExamRepository;
import com.sebrown2023.repository.ExamSessionRepository;
import com.sebrown2023.repository.TaskRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Deprecated
@Service
@AllArgsConstructor
public class ExamCreateService {

    private ExamRepository examRepository;
    private ExamSessionRepository examSessionRepository;
    private TaskRepository taskRepository;

    private ExamComponentToExamMapper examMapper;
    private TaskComponentToTaskMapper taskMapper;



//    public void createNewFullExam(ExamComponent examComponent) {
//        Exam savedExam = examRepository.save(examMapper.convert(examComponent));
//
//        examComponent.getTasks().forEach( taskComponent -> {
//            Task task = taskMapper.convert(taskComponent);
//            task.setExam(savedExam);
//            Task savedTask = taskRepository.save(task);
//
//            taskComponent.getTests().forEach( testComponent -> {
//
//            });
//        });
//
//    }

    public void updateExam() {

    }

    public void deleteExam() {

    }
}
