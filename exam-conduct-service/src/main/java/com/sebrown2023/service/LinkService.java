package com.sebrown2023.service;

import com.sebrown2023.model.db.Link;

public interface LinkService {
    boolean checkExpired(Link link);
}
