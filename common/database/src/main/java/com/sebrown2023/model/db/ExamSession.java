package com.sebrown2023.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private Exam exam;
    @ManyToOne()
    private Examinee examine;
    @Column(nullable = false)
    private Status status;
    @Column
    private LocalDateTime startTimestamp;
    @Column
    private LocalDateTime finishTimestamp;

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
