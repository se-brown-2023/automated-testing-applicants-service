package com.sebrown2023.repository;

import com.sebrown2023.model.db.Examinee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamineeRepository extends JpaRepository<Examinee, Long> {
}
