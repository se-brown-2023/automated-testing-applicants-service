package com.sebrown2023.compiler;

import com.sebrown2023.compiler.model.Stream;
import com.sebrown2023.configuration.ExamJudgingConfiguration;
import com.sebrown2023.configuration.properties.CompilersProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootTest(classes = {JavaCompiler.class, ExamJudgingConfiguration.class})
@EnableConfigurationProperties(value = {CompilersProperties.class})
@ActiveProfiles("test")
class JavaCompilerTest {
    @Autowired
    private JavaCompiler javaCompiler;

    @Test
    void testSuccessfulJavaCompilation() throws IOException, ExecutionException {
        var javaFile = new File(this.getClass().getResource("/javaCompilerTestData/EverythingIsOk.java").getPath());
        var pathToCompiled = Files.createTempDirectory("javaCompiler");
        var javaCompilerInvokeResult = javaCompiler.invokeCompiler(List.of(javaFile), pathToCompiled.toString());
        Assertions.assertTrue(javaCompilerInvokeResult.isCompileSuccess());
        Assertions.assertEquals(2, Files.walk(pathToCompiled).count()); // dir and Solution.class
        Assertions.assertTrue(Files.exists(pathToCompiled.resolve("Solution.class")));

        var result = javaCompiler.executeCompiled(pathToCompiled.toString(), Stream.BOTH, List.of("Solution"), 5L);
        Assertions.assertTrue(result.errorStream().isEmpty());
        Assertions.assertEquals("[[1, 6], [8, 10], [15, 18]]", result.outputStream());
    }

    @Test
    void testFailedJavaCompilation() throws IOException, ExecutionException {
        var javaFile = new File(this.getClass().getResource("/javaCompilerTestData/BadSolution.java").getPath());
        var pathToCompiled = Files.createTempDirectory("javaCompiler");
        var javaCompilerInvokeResult = javaCompiler.invokeCompiler(List.of(javaFile), pathToCompiled.toString());
        Assertions.assertFalse(javaCompilerInvokeResult.isCompileSuccess());
        Assertions.assertEquals("';' expected", javaCompilerInvokeResult.combinedOutput());
    }

    @Test
    void testExecutionTimeout() throws IOException, ExecutionException {
        var javaFile = new File(this.getClass().getResource("/javaCompilerTestData/ExecutionTimeout.java").getPath());
        var pathToCompiled = Files.createTempDirectory("javaCompiler");
        var javaCompilerInvokeResult = javaCompiler.invokeCompiler(List.of(javaFile), pathToCompiled.toString());
        Assertions.assertTrue(javaCompilerInvokeResult.isCompileSuccess());
        Assertions.assertEquals(2, Files.walk(pathToCompiled).count()); // dir and Solution.class
        Assertions.assertTrue(Files.exists(pathToCompiled.resolve("Solution.class")));

        var result = javaCompiler.executeCompiled(pathToCompiled.toString(), Stream.BOTH, List.of("Solution"), 5L);
        Assertions.assertEquals("Exception timeout", result.outputStream());
    }
}