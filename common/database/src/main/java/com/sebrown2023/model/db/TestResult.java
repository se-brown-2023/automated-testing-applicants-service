package com.sebrown2023.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Submission submission;

    @ManyToOne(optional = false)
    private Test test;

    @Column(nullable = false)
    private String actualOutputData;

    @Column(nullable = false)
    private Boolean passed;

    @Column(nullable = false)
    private Duration elapsedTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestResult that = (TestResult) o;

        if (!id.equals(that.id)) return false;
        if (!submission.equals(that.submission)) return false;
        if (!test.equals(that.test)) return false;
        if (!actualOutputData.equals(that.actualOutputData)) return false;
        if (!passed.equals(that.passed)) return false;
        return elapsedTime.equals(that.elapsedTime);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + submission.hashCode();
        result = 31 * result + test.hashCode();
        result = 31 * result + actualOutputData.hashCode();
        result = 31 * result + passed.hashCode();
        result = 31 * result + elapsedTime.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "id=" + id +
                ", submission=" + submission +
                ", test=" + test +
                ", actualOutputData='" + actualOutputData + '\'' +
                ", passed=" + passed +
                ", elapsedTime=" + elapsedTime +
                '}';
    }
}
