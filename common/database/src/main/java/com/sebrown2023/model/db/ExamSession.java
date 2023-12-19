package com.sebrown2023.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ExamSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private Exam exam;
    @ManyToOne
    private Examinee examine;
    @Column(nullable = false)
    private Status status;
    @Column
    private LocalDateTime startTimestamp;
    @Column
    private LocalDateTime finishTimestamp;

    public ExamSession() {

    }

    public ExamSession(
            Exam exam,
            Examinee examine,
            Status status,
            LocalDateTime startTimestamp,
            LocalDateTime finishTimestamp
    ) {
        this.exam = exam;
        this.examine = examine;
        this.status = status;
        this.startTimestamp = startTimestamp;
        this.finishTimestamp = finishTimestamp;
    }

    public UUID getId() {
        return id;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Examinee getExamine() {
        return examine;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getStartTimestamp() {
        return startTimestamp;
    }

    public LocalDateTime getFinishTimestamp() {
        return finishTimestamp;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStartTimestamp(LocalDateTime startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public void setFinishTimestamp(LocalDateTime finishTimestamp) {
        this.finishTimestamp = finishTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExamSession that = (ExamSession) o;

        if (!id.equals(that.id)) return false;
        if (!exam.equals(that.exam)) return false;
        if (!Objects.equals(examine, that.examine)) return false;
        if (status != that.status) return false;
        if (!Objects.equals(startTimestamp, that.startTimestamp))
            return false;
        return Objects.equals(finishTimestamp, that.finishTimestamp);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + exam.hashCode();
        result = 31 * result + examine.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + (startTimestamp != null ? startTimestamp.hashCode() : 0);
        result = 31 * result + (finishTimestamp != null ? finishTimestamp.hashCode() : 0);
        return result;
    }
}
