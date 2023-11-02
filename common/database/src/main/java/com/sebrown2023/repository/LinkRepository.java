package com.sebrown2023.repository;

import com.sebrown2023.model.db.Link;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, Long> {
    Link findByUUID(String uuid);
}
