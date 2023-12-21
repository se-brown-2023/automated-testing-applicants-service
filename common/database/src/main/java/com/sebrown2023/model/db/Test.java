package com.sebrown2023.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
