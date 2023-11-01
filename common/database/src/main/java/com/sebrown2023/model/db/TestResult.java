package com.sebrown2023.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.Duration;

@Entity
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

    public TestResult() {

    }

    public TestResult(Submission submission, Test test, String actualOutputData, Boolean passed, Duration elapsedTime) {
        this.submission = submission;
        this.test = test;
        this.actualOutputData = actualOutputData;
        this.passed = passed;
        this.elapsedTime = elapsedTime;
    }

    public Long getId() {
        return id;
    }

    public Submission getSubmission() {
        return submission;
    }

    public Test getTest() {
        return test;
    }

    public String getActualOutputData() {
        return actualOutputData;
    }

    public Boolean getPassed() {
        return passed;
    }

    public Duration getElapsedTime() {
        return elapsedTime;
    }

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
