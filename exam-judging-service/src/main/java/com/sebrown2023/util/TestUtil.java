package com.sebrown2023.util;

import com.sebrown2023.model.WrappedCode;
import com.sebrown2023.model.db.ProgrammingLanguage;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class TestUtil {
    private TestUtil() {
    }

    private static final String JAVA_MAIN_CLASS_PLACEHOLDER = "CLASS_RANDOM_NUMBER";
    private static final String JAVA_RETURN_TYPE_PLACEHOLDER = "REPLACE_WITH_RETURN_TYPE";
    private static final Pattern JAVA_SOLUTION_METHOD_PATTERN =
            Pattern.compile("public +(final +)?(.*) +solve *\\(.*\\).*");

    public static WrappedCode addMainFunction(final String sourceCode, ProgrammingLanguage language) throws IOException {
        switch (language) {
            case JAVA -> {
                int mainMethodPostfix = new SecureRandom().nextInt(0, Integer.MAX_VALUE);
                Path javaTemplate = Path.of(TestUtil.class.getResource("/codeTemplates/java-template.java").getPath());
                Path tmpDir = Files.createTempDirectory("javaSubmission");
                String mainClass = Files.readString(javaTemplate)
                        .replace(JAVA_MAIN_CLASS_PLACEHOLDER, String.valueOf(mainMethodPostfix))
                        .replace(JAVA_RETURN_TYPE_PLACEHOLDER, extractSolutionReturnType(sourceCode));
                var tmpMainFile = tmpDir.resolve(STR. "Main_\{ mainMethodPostfix }.java" );
                Files.writeString(
                        tmpMainFile,
                        mainClass,
                        StandardOpenOption.WRITE,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING
                );
                var tmpSolutionFile = tmpDir.resolve("Solution.java");
                Files.writeString(
                        tmpSolutionFile,
                        sourceCode,
                        StandardOpenOption.WRITE,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING
                );
                return new WrappedCode(
                        STR. "Main_\{ mainMethodPostfix }" ,
                        List.of(tmpMainFile, tmpSolutionFile),
                        tmpDir
                );
            }
            case JAVA_SCRIPT -> throw new UnsupportedOperationException();
            case null, default -> throw new IllegalArgumentException();
        }
    }

    static String extractSolutionReturnType(final String sourceCode) {
        for (String line : sourceCode.split("\n")) {
            Matcher matcher = JAVA_SOLUTION_METHOD_PATTERN.matcher(line);
            if (matcher.find()) {
                return replacePrimitiveWithWrapper(matcher.group(2));
            }
        }

        return "com.fasterxml.jackson.databind.node.ObjectNode";
    }

    static String replacePrimitiveWithWrapper(String type) {
        return switch (type) {
            case "int" -> "Integer";
            case "long" -> "Long";
            case "double" -> "Double";
            case "float" -> "Float";
            case "short" -> "Short";
            case "boolean" -> "Boolean";
            case "byte" -> "Byte";
            case "char" -> "Char";
            case null, default -> type;
        };
    }

    public static void copyJavaLibs(String libDir, Path sourceDir) {
        if (!Files.exists(sourceDir)) {
            try {
                Files.createDirectory(sourceDir);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        try (Stream<Path> paths = Files.walk(Path.of(libDir), 1)) {
            paths.filter(Files::isRegularFile)
                    .forEach(lib -> {
                        try {
                            Files.copy(lib, sourceDir.resolve(lib.getFileName()));
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String prepareJavaPathToCompiled(WrappedCode wrappedCode) {
        var compiledPath = wrappedCode.sourceDir().resolve("compiled");
        try (Stream<Path> paths = Files.walk(compiledPath, 1)) {
            List<String> classPath = new java.util.ArrayList<>(paths.filter(Files::isRegularFile)
                    .filter(file -> file.getFileName().toString().endsWith(".jar"))
                    .map(Path::toString)
                    .toList());
            classPath.add(compiledPath.toString());
            return String.join(":", classPath);
        } catch (IOException ioException) {
            throw new UncheckedIOException(ioException);
        }
    }
}
