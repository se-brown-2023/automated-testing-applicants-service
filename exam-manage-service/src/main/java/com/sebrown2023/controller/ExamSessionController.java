package com.sebrown2023.controller;

import com.sebrown2023.service.ExamSessionService;
import com.sebrown2023.model.db.ExamSession;
import com.sebrown2023.model.exceptions.ExamSessionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@ControllerAdvice
@RequestMapping("/api/exam/session")
public class ExamSessionController {

    @Autowired
    private ExamSessionService sessionService;

    @GetMapping(value = "/get/{uuid}")
    public ResponseEntity<ExamSession> getSessionById(@PathVariable("uuid") UUID sessionId) throws ExamSessionNotFoundException {
        ExamSession session = sessionService.get(sessionId);

        return ResponseEntity.ok(session);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ExamSession> createSession() {
        ExamSession session = sessionService.create();

        return ResponseEntity.ok(session);
    }

    @DeleteMapping(value = "/delete/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSession(@PathVariable("uuid") UUID sessionId) throws ExamSessionNotFoundException {
        sessionService.deleteSession(sessionId);
    }

}
