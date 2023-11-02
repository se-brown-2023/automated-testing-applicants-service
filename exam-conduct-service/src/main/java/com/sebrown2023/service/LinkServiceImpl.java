package com.sebrown2023.service;

import com.sebrown2023.model.db.Link;
import com.sebrown2023.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    private LinkRepository linkRepository;

    @Override
    public boolean checkExpired(Link link) {
        boolean expired = link.isExpired();

        if (expired) {
            return true;
        } else {
            expired = LocalDateTime.now().isAfter(link.getCreationTime().plus(link.getLinkTTL()));
            link.setExpired(expired);
            linkRepository.save(link);
            return expired;
        }
    }
}
