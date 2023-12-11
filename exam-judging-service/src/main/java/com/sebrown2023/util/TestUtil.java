package com.sebrown2023.util;

import com.sebrown2023.model.WrappedCode;
import com.sebrown2023.model.db.ProgrammingLanguage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                var tmpMainFile = tmpDir.resolve("Main.java");
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
                return matcher.group(2);
            }
        }

        return "com.fasterxml.jackson.databind.node.ObjectNode";
    }
}
