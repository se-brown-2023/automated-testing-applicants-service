package com.sebrown2023.dto;

import com.sebrown2023.model.db.Submission;
import com.sebrown2023.model.db.Test;
import jakarta.persistence.*;

import java.time.Duration;

public record TestResultDto (
    Long id,
    String actualOutputData,
    Boolean passed,
    Duration elapsedTime
) {}
