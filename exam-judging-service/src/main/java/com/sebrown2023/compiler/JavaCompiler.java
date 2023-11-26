package com.sebrown2023.compiler;

import com.sebrown2023.compiler.model.CompilerMessageLocation;
import com.sebrown2023.compiler.model.ExecutionsStatus;
import com.sebrown2023.compiler.model.InvokeStatus;
import com.sebrown2023.compiler.model.JavacInvokeStatus;
import com.sebrown2023.compiler.model.Stream;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Component
public class JavaCompiler extends BaseCompiler {
    @Qualifier("javaCompilerExecutorService")
    private final ExecutorService threadPool;

    public JavaCompiler(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    InvokeStatus invokeCompiler(List<File> files, String pathToCompiled) throws IOException, ExecutionException {

        // Get the system Java compiler and initialize its components
        javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager manager = compiler.getStandardFileManager(diagnostics, null, null);
        Iterable<? extends JavaFileObject> sources = manager.getJavaFileObjectsFromFiles(files);

        // Create the output directory if it doesn't already exist
        new File(pathToCompiled).mkdirs();

        // Set up the compiler options and create the compilation task
        var options = List.of("-d", pathToCompiled);
        var task = compiler.getTask(null, manager, diagnostics, options, null, sources);

        // Start the compiler task in a new thread
        var futureExitCode = threadPool.submit(task);

        // Initialize flags for tracking the compilation process
        var hasTimeout = false;
        var compilerWorkingTime = -1L;

        try {
            // Start a timer to keep track of how long the compilation runs for
            var startTime = System.currentTimeMillis();
            futureExitCode.get(10L, TimeUnit.SECONDS);
            compilerWorkingTime = System.currentTimeMillis() - startTime;
        } catch (TimeoutException ignored) {
            // The compilation timed out
            hasTimeout = true;
            futureExitCode.cancel(true);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        return new JavacInvokeStatus(
                diagnostics.getDiagnostics()
                        .stream()
                        .map(it -> it.getMessage(Locale.getDefault()))
                        .collect(Collectors.joining("\n")),
                diagnostics.getDiagnostics()
                        .stream()
                        .noneMatch(diagnostic -> diagnostic.getKind() == Diagnostic.Kind.ERROR),
                false,
                hasTimeout,
                compilerWorkingTime,
                diagnostics.getDiagnostics()
                        .stream()
                        .filter(diagnostic -> diagnostic.getKind() == Diagnostic.Kind.ERROR)
                        .map(diagnostic ->
                                Objects.requireNonNull(
                                        CompilerMessageLocation.create(
                                                diagnostic.getSource().getName(),
                                                (int) diagnostic.getLineNumber(),
                                                (int) diagnostic.getColumnNumber(),
                                                diagnostic.toString()
                                        )
                                )
                        ).toList()
        );
    }

    @Override
    ExecutionsStatus executeCompiled(String pathToCompiled, Stream streamType, List<String> args, long timeoutSec) {
        var classPath = pathToCompiled;
        var mainClass = String.join(" ", args);
        try {
            return commonExecution(STR. "java -classpath \{ classPath } \{ mainClass }" , streamType, timeoutSec);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
