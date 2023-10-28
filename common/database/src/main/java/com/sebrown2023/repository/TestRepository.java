package com.sebrown2023.repository;

import com.sebrown2023.model.db.Test;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<Test, Long> {
}
