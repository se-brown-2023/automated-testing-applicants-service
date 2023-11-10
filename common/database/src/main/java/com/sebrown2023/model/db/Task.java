package com.sebrown2023.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
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

    public Task() {

    }

    public Task( Exam exam, String name, String description, String authorSourceCode) {
        this.exam = exam;
        this.name = name;
        this.description = description;
        this.authorSourceCode = authorSourceCode;
    }

    public Long getId() {
        return id;
    }

    public Exam getExam() {
        return exam;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthorSourceCode() {
        return authorSourceCode;
    }

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
        result = 31 * result + exam.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (authorSourceCode != null ? authorSourceCode.hashCode() : 0);
        return result;
    }
}
