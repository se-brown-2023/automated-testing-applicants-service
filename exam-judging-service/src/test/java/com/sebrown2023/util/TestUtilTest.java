package com.sebrown2023.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestUtilTest {

    @Test
    void extractSolutionReturnTypeTest() throws IOException {
        var javaFile = Path.of(this.getClass().getResource("/javaCompilerTestData/Solution_1.java").getPath());
        assertEquals("int[][]", TestUtil.extractSolutionReturnType(Files.readString(javaFile)));

        javaFile = Path.of(this.getClass().getResource("/javaCompilerTestData/Solution_2.java").getPath());
        assertEquals("ListNode", TestUtil.extractSolutionReturnType(Files.readString(javaFile)));

        javaFile = Path.of(this.getClass().getResource("/javaCompilerTestData/Solution_3.java").getPath());
        assertEquals("Boolean", TestUtil.extractSolutionReturnType(Files.readString(javaFile)));

        javaFile = Path.of(this.getClass().getResource("/javaCompilerTestData/Solution_4.java").getPath());
        assertEquals("List<Map<String, Tuple<Integer, String>>>", TestUtil.extractSolutionReturnType(Files.readString(javaFile)));

        javaFile = Path.of(this.getClass().getResource("/javaCompilerTestData/BadSolution.java").getPath());
        assertEquals("com.fasterxml.jackson.databind.node.ObjectNode", TestUtil.extractSolutionReturnType(Files.readString(javaFile)));
    }
}