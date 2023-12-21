package com.sebrown2023.controllers;

import com.sebrown2023.dto.deprecated.GetSubmissionDto;
import com.sebrown2023.dto.deprecated.PostSubmissionDto;
import com.sebrown2023.services.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/submission")
public class SubmissionController {

    private final SubmissionService submissionService;

    @GetMapping(value = "/{submissionId}")
    public ResponseEntity<GetSubmissionDto> getSubmission(@PathVariable long submissionId) {
        return ResponseEntity.ok().body(submissionService.getSubmissionDtoById(submissionId));
    }

    @GetMapping()
    public ResponseEntity<List<GetSubmissionDto>> getAllSubmissions() {
        return ResponseEntity.ok().body(submissionService.getAllSubmissionsDto());
    }

    //TODO скорее всего submissions не должны создаваться в manage-service
    @PostMapping()
    public ResponseEntity<?> createSubmission(PostSubmissionDto postSubmissionDto) {
        return ResponseEntity.ok().build();
    }
}
