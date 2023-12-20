package com.sebrown2023.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Task task;
    @ManyToOne(optional = false)
    private ExamSession examSession;
    @Column
    private String userSourceCode;
    @Column(nullable = false)
    private Date submitTime;

    public Submission(Task task, ExamSession examSession, String userSourceCode, Date submitTime) {
        this.task = task;
        this.examSession = examSession;
        this.userSourceCode = userSourceCode;
        this.submitTime = submitTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Submission that = (Submission) o;

        if (!id.equals(that.id)) return false;
        if (!task.equals(that.task)) return false;
        if (!examSession.equals(that.examSession)) return false;
        if (!Objects.equals(userSourceCode, that.userSourceCode))
            return false;
        return submitTime.equals(that.submitTime);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + task.hashCode();
        result = 31 * result + examSession.hashCode();
        result = 31 * result + (userSourceCode != null ? userSourceCode.hashCode() : 0);
        result = 31 * result + submitTime.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "id=" + id +
                ", task=" + task +
                ", examSession=" + examSession +
                ", userSourceCode='" + userSourceCode + '\'' +
                ", submitTime=" + submitTime +
                '}';
    }
}
