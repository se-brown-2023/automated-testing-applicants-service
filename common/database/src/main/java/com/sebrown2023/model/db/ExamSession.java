package com.sebrown2023.model.db;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class ExamSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    private Exam exam;
    @ManyToOne()
    private Examinee examine;
    @Column(nullable = false)
    private Status status;
    @Column
    private Date starTimestamp;
    @Column
    private Date finishTimestamp;
    @Column(nullable = false)
    private Boolean expired;

    public ExamSession() {

    }

    public ExamSession(
            Exam exam,
            Examinee examine,
            Status status,
            Date starTimestamp,
            Date finishTimestamp,
            Boolean expired
    ) {
        this.exam = exam;
        this.examine = examine;
        this.status = status;
        this.starTimestamp = starTimestamp;
        this.finishTimestamp = finishTimestamp;
        this.expired = expired;
    }

    public Long getId() {
        return id;
    }

    public Exam getExam() {
        return exam;
    }

    public Examinee getExamine() {
        return examine;
    }

    public Status getStatus() {
        return status;
    }

    public Date getStarTimestamp() {
        return starTimestamp;
    }

    public Date getFinishTimestamp() {
        return finishTimestamp;
    }

    public Boolean getExpired() {
        return expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExamSession that = (ExamSession) o;

        if (!id.equals(that.id)) return false;
        if (!exam.equals(that.exam)) return false;
        if (!examine.equals(that.examine)) return false;
        if (status != that.status) return false;
        if (!Objects.equals(starTimestamp, that.starTimestamp))
            return false;
        if (!Objects.equals(finishTimestamp, that.finishTimestamp))
            return false;
        return expired.equals(that.expired);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + exam.hashCode();
        result = 31 * result + examine.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + (starTimestamp != null ? starTimestamp.hashCode() : 0);
        result = 31 * result + (finishTimestamp != null ? finishTimestamp.hashCode() : 0);
        result = 31 * result + expired.hashCode();
        return result;
    }
}
