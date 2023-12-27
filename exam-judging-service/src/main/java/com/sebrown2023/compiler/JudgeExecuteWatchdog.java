package com.sebrown2023.compiler;

import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Watchdog;
import org.apache.commons.exec.util.DebugUtils;

public class JudgeExecuteWatchdog extends ExecuteWatchdog {

    /**
     * The marker for an infinite timeout
     */
    public static final long INFINITE_TIMEOUT = -1;

    /**
     * The process to execute and watch for duration.
     */
    private Process process;

    /**
     * Is a user-supplied timeout in use
     */
    private final boolean hasWatchdog;

    /**
     * Say whether or not the watchdog is currently monitoring a process.
     */
    private boolean watch;

    /**
     * Exception that might be thrown during the process execution.
     */
    private Exception caught;

    /**
     * Say whether or not the process was killed due to running overtime.
     */
    private boolean killedProcess;

    /**
     * Will tell us whether timeout has occurred.
     */
    private final Judge watchdog;

    /**
     * Indicates that the process is verified as started
     */
    private volatile boolean processStarted;

    /**
     * Creates a new watchdog with a given timeout.
     *
     * @param timeout the timeout for the process in milliseconds. It must be
     *                greater than 0 or 'INFINITE_TIMEOUT'
     */
    public JudgeExecuteWatchdog(final long timeout) {
        super(timeout);

        this.killedProcess = false;
        this.watch = false;
        this.hasWatchdog = timeout != INFINITE_TIMEOUT;
        this.processStarted = false;
        if (this.hasWatchdog) {
            this.watchdog = new Judge(timeout);
            this.watchdog.addTimeoutObserver(this);
        } else {
            this.watchdog = null;
        }
    }

    /**
     * Watches the given process and terminates it, if it runs for too long. All
     * information from the previous run are reset.
     *
     * @param processToMonitor the process to monitor. It cannot be {@code null}
     * @throws IllegalStateException if a process is still being monitored.
     */
    @Override
    public synchronized void start(final Process processToMonitor) {
        if (processToMonitor == null) {
            throw new NullPointerException("process is null.");
        }
        if (this.process != null) {
            throw new IllegalStateException("Already running.");
        }
        this.caught = null;
        this.killedProcess = false;
        this.watch = true;
        this.process = processToMonitor;
        this.processStarted = true;
        this.notifyAll();
        if (this.hasWatchdog) {
            watchdog.start();
        }
    }

    /**
     * Stops the watcher. It will notify all threads possibly waiting on this
     * object.
     */
    @Override
    public synchronized void stop() {
        if (hasWatchdog) {
            watchdog.stop();
        }
        watch = false;
        process = null;
    }

    /**
     * Destroys the running process manually.
     */
    @Override
    public synchronized void destroyProcess() {
        ensureProcessStarted();
        this.timeoutOccured(null);
        this.stop();
    }

    /**
     * Called after watchdog has finished.
     */
    @Override
    public synchronized void timeoutOccured(final Watchdog w) {
        try {
            try {
                // We must check if the process was not stopped
                // before being here
                if (process != null) {
                    process.exitValue();
                }
            } catch (final IllegalThreadStateException itse) {
                // the process is not terminated, if this is really
                // a timeout and not a manual stop then destroy it.
                if (watch) {
                    killedProcess = true;
                    process.destroy();
                }
            }
        } catch (final Exception e) {
            caught = e;
            DebugUtils.handleException("Getting the exit value of the process failed", e);
        } finally {
            cleanUp();
        }
    }


    /**
     * This method will rethrow the exception that was possibly caught during
     * the run of the process. It will only remains valid once the process has
     * been terminated either by 'error', timeout or manual intervention.
     * Information will be discarded once a new process is ran.
     *
     * @throws Exception a wrapped exception over the one that was silently swallowed
     *                   and stored during the process run.
     */
    @Override
    public synchronized void checkException() throws Exception {
        if (caught != null) {
            throw caught;
        }
    }

    /**
     * Indicates whether or not the watchdog is still monitoring the process.
     *
     * @return {@code true} if the process is still running, otherwise
     * {@code false}.
     */
    @Override
    public synchronized boolean isWatching() {
        ensureProcessStarted();
        return watch;
    }

    /**
     * Indicates whether the last process run was killed.
     *
     * @return {@code true} if the process was killed
     * {@code false}.
     */
    @Override
    public synchronized boolean killedProcess() {
        return killedProcess;
    }

    /**
     * reset the monitor flag and the process.
     */
    @Override
    protected synchronized void cleanUp() {
        watch = false;
        process = null;
    }

    /**
     * Ensures that the process is started, so we do not race with asynch execution.
     * The caller of this method must be holding the lock on this
     */
    private synchronized void ensureProcessStarted() {
        while (!processStarted) {
            try {
                this.wait();
            } catch (final InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public long getExecutionTimeMs() {
        return watchdog.getExecutionTimeMs();
    }
}

