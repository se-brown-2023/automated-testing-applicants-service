package com.sebrown2023.repository;

import com.sebrown2023.model.db.Examinee;
import org.springframework.data.repository.CrudRepository;

public interface ExamineeRepository extends CrudRepository<Examinee, Long> {
}
