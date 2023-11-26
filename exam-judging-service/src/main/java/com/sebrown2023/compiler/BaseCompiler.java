package com.sebrown2023.compiler;

import com.sebrown2023.compiler.model.ExecutionsStatus;
import com.sebrown2023.compiler.model.InvokeStatus;
import com.sebrown2023.compiler.model.Stream;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class BaseCompiler {
    abstract InvokeStatus invokeCompiler(List<File> files, String pathToCompiled) throws IOException, ExecutionException;

    abstract ExecutionsStatus executeCompiled(String pathToCompiled, Stream streamType, List<String> args, long timeoutSec);

    public ExecutionsStatus commonExecution(String command, Stream streamType, long timeoutSec) throws IOException {
        CommandLine cmdLine = CommandLine.parse(command);
        var outputStream = new ByteArrayOutputStream();
        var errorStream = new ByteArrayOutputStream();
        var executor = new DefaultExecutor();
        executor.setWatchdog(new JudgeExecuteWatchdog(timeoutSec * 1000));
        executor.setStreamHandler(new PumpStreamHandler(outputStream, errorStream));
        try {
            executor.execute(cmdLine);
        } catch (ExecuteException e) {
            executor.getWatchdog().destroyProcess();
            var streamOutput = switch (streamType) {
                case Stream.INPUT -> outputStream.toString();
                case Stream.ERROR -> errorStream.toString();
                default -> "" + errorStream;
            };
            if (errorStream.toString().isEmpty() || errorStream.toString().toLowerCase().contains("stackoverflow")) {
                streamOutput = "Exception timeout";
            }
            var time = ((JudgeExecuteWatchdog) executor.getWatchdog()).getExecutionTimeMs();
            return new ExecutionsStatus(streamOutput, streamOutput, time);
        }
        var outputStreamString = outputStream.toString();
        outputStreamString = outputStreamString.substring(0, Math.max(0, outputStreamString.length() - 1)); // drop last newline

        var errorStreamString = errorStream.toString();
        errorStreamString = errorStreamString.substring(0, Math.max(0, errorStreamString.length() - 1));
        var time = ((JudgeExecuteWatchdog) executor.getWatchdog()).getExecutionTimeMs();
        return switch (streamType) {
            case Stream.INPUT -> new ExecutionsStatus(outputStreamString, null, time);
            case Stream.ERROR -> new ExecutionsStatus(null, errorStreamString, time);
            case Stream.BOTH -> new ExecutionsStatus(outputStreamString, errorStreamString, time);
        };
    }
}
