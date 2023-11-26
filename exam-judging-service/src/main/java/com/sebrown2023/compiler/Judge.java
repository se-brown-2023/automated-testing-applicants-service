package com.sebrown2023.compiler;

import org.apache.commons.exec.TimeoutObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class Judge implements Runnable {
    private final ArrayList<TimeoutObserver> observers = new ArrayList<>(1);

    private final long timeout;

    private boolean stopped = false;

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    private volatile long executionTimeMs;

    public Judge(final long timeout) {
        if (timeout < 1) {
            throw new IllegalArgumentException("timeout must not be less than 1.");
        }
        this.timeout = timeout;
    }

    public void addTimeoutObserver(final TimeoutObserver to) {
        observers.add(to);
    }

    protected final void fireTimeoutOccurred() {
        final Enumeration<TimeoutObserver> e = Collections.enumeration(observers);
        while (e.hasMoreElements()) {
            e.nextElement().timeoutOccured(null);
        }
    }

    public synchronized void start() {
        stopped = false;
        final Thread t = new Thread(this, "WATCHDOG");
        t.setDaemon(true);
        t.start();
    }

    public synchronized void stop() {
        stopped = true;
        notifyAll();
    }

    public void run() {
        final long startTime = System.currentTimeMillis();
        boolean isWaiting;
        synchronized (this) {
            executionTimeMs = System.currentTimeMillis() - startTime;
            long timeLeft = timeout - (executionTimeMs);
            isWaiting = timeLeft > 0;
            while (!stopped && isWaiting) {
                try {
                    wait(timeLeft);
                } catch (final InterruptedException e) {
                    // ignore
                }
                executionTimeMs = System.currentTimeMillis() - startTime;
                timeLeft = timeout - (executionTimeMs);
                isWaiting = timeLeft > 0;
            }
        }

        // notify the listeners outside of the synchronized block (see EXEC-60)
        if (!isWaiting) {
            fireTimeoutOccurred();
        }
    }
}
