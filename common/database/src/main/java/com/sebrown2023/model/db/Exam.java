package com.sebrown2023.model.db;

import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.Type;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer examinerId;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private String programmingLanguage;

    @Type(PostgreSQLIntervalType.class)
    @Column(columnDefinition = "interval")
    private Duration maxDuration;

    @Type(PostgreSQLIntervalType.class)
    @Column(columnDefinition = "interval")
    private Duration ttl;

    @Column
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "exam")
    private List<Task> tasks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exam exam = (Exam) o;

        if (!id.equals(exam.id)) return false;
        if (!Objects.equals(examinerId, exam.examinerId)) return false;
        if (!name.equals(exam.name)) return false;
        if (!Objects.equals(description, exam.description)) return false;
        if (!programmingLanguage.equals(exam.programmingLanguage)) return false;
        if (!Objects.equals(maxDuration, exam.maxDuration)) return false;
        if (!Objects.equals(ttl, exam.ttl)) return false;
        return Objects.equals(creationDate, exam.creationDate);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (examinerId != null ? examinerId.hashCode() : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + programmingLanguage.hashCode();
        result = 31 * result + (maxDuration != null ? maxDuration.hashCode() : 0);
        result = 31 * result + (ttl != null ? ttl.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", examinerId=" + examinerId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", programmingLanguage='" + programmingLanguage + '\'' +
                ", maxDuration=" + maxDuration +
                ", ttl=" + ttl +
                ", creationDate=" + creationDate +
                '}';
    }
}
