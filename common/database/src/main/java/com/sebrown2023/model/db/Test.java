package com.sebrown2023.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Task task;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String inputData;
    @Column(nullable = false)
    private String expectedOutputData;

    public Test() {
    }

    public Test( Task task, String name, String inputData, String expectedOutputData) {
        this.task = task;
        this.name = name;
        this.inputData = inputData;
        this.expectedOutputData = expectedOutputData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Test test = (Test) o;

        if (!id.equals(test.id)) return false;
        if (!task.equals(test.task)) return false;
        if (!name.equals(test.name)) return false;
        if (!inputData.equals(test.inputData)) return false;
        return expectedOutputData.equals(test.expectedOutputData);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + task.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + inputData.hashCode();
        result = 31 * result + expectedOutputData.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", task=" + task +
                ", name='" + name + '\'' +
                ", inputData='" + inputData + '\'' +
                ", expectedOutputData='" + expectedOutputData + '\'' +
                '}';
    }
}
