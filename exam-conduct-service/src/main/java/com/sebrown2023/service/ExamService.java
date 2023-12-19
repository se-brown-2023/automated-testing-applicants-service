package com.sebrown2023.service;

import com.sebrown2023.model.db.Exam;
import com.sebrown2023.model.exceptions.ExamSessionException;

import java.util.UUID;

public interface ExamService {
    Exam getExamByUUID(UUID uuid) throws ExamSessionException;

}
