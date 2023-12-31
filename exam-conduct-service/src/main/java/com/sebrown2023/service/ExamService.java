package com.sebrown2023.service;

import com.sebrown2023.exceptions.ExamNotFoundException;
import com.sebrown2023.model.db.Exam;
import com.sebrown2023.exceptions.ExamSessionException;

import java.util.UUID;

public interface ExamService {
    Exam getExamByUUID(UUID uuid) throws ExamSessionException;
    Exam getExamById(Long examId) throws ExamNotFoundException;

}
