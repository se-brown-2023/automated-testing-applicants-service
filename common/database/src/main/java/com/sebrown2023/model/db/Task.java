package com.sebrown2023.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Exam exam;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String authorSourceCode;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", exam=" + exam +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", authorSourceCode='" + authorSourceCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!id.equals(task.id)) return false;
        if (!exam.equals(task.exam)) return false;
        if (!Objects.equals(name, task.name)) return false;
        if (!Objects.equals(description, task.description)) return false;
        return Objects.equals(authorSourceCode, task.authorSourceCode);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (exam != null ? exam.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (authorSourceCode != null ? authorSourceCode.hashCode() : 0);
        return result;
    }
}
