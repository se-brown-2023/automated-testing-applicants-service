package com.sebrown2023.services;

import com.sebrown2023.exceptions.NoElementException;
import com.sebrown2023.mappers.ExamineeMapper;
import com.sebrown2023.model.dto.ExamineeComponent;
import com.sebrown2023.repository.ExamineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamineeService {
    private final ExamineeRepository examineeRepository;
    private final ExamineeMapper examineeMapper;

    public ExamineeComponent createExaminee(ExamineeComponent examineeComponent) {
        var createdExaminee = examineeRepository.save(examineeMapper.examineeComponentToExaminee(examineeComponent));
        return examineeMapper.examineeToExamineeComponent(createdExaminee);
    }


    public ExamineeComponent getExamineeById(Long examineeId) {
        var examinee = examineeRepository.findById(examineeId)
                .orElseThrow(() -> new NoElementException("Examinee with id " + examineeId));
        return examineeMapper.examineeToExamineeComponent(examinee);
    }


    public List<ExamineeComponent> getExaminees() {
        return examineeRepository.findAll().stream().map(examineeMapper::examineeToExamineeComponent).toList();
    }
}
