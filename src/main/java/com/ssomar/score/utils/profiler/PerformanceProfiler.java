package com.ssomar.score.utils.profiler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Performance profiler utility for tracking execution times of commands, features, and events.
 * This helps identify performance bottlenecks in your plugin.
 *
 * Usage:
 * <pre>
 * PerformanceProfiler.start("myCommand");
 * // ... your code ...
 * PerformanceProfiler.stop("myCommand");
 *
 * // Get report
 * ProfilerReport report = PerformanceProfiler.getReport("myCommand");
 * </pre>
 */
public class PerformanceProfiler {

    private static final Map<String, ProfilerData> profilerDataMap = new ConcurrentHashMap<>();
    private static final ThreadLocal<Map<String, Long>> threadLocalStartTimes = ThreadLocal.withInitial(HashMap::new);
    private static volatile boolean enabled = true;
    private static final int MAX_STORED_OPERATIONS = 10000;

    /**
     * Starts profiling for a specific operation
     * @param operationName The name of the operation to profile
     */
    public static void start(String operationName) {
        if (!enabled) return;

        threadLocalStartTimes.get().put(operationName, System.nanoTime());
    }

    /**
     * Stops profiling for a specific operation and records the execution time
     * @param operationName The name of the operation that was being profiled
     */
    public static void stop(String operationName) {
        if (!enabled) return;

        Long startTime = threadLocalStartTimes.get().remove(operationName);
        if (startTime == null) {
            return;
        }

        long duration = System.nanoTime() - startTime;

        profilerDataMap.computeIfAbsent(operationName, k -> new ProfilerData())
                .recordExecution(duration);
    }

    /**
     * Profiles a runnable operation
     * @param operationName The name of the operation
     * @param runnable The operation to profile
     */
    public static void profile(String operationName, Runnable runnable) {
        start(operationName);
        try {
            runnable.run();
        } finally {
            stop(operationName);
        }
    }

    /**
     * Gets a profiler report for a specific operation
     * @param operationName The name of the operation
     * @return ProfilerReport containing statistics, or null if no data exists
     */
    public static ProfilerReport getReport(String operationName) {
        ProfilerData data = profilerDataMap.get(operationName);
        if (data == null) return null;

        return data.generateReport(operationName);
    }

    /**
     * Gets reports for all profiled operations
     * @return List of all profiler reports
     */
    public static List<ProfilerReport> getAllReports() {
        List<ProfilerReport> reports = new ArrayList<>();
        for (Map.Entry<String, ProfilerData> entry : profilerDataMap.entrySet()) {
            reports.add(entry.getValue().generateReport(entry.getKey()));
        }
        return reports;
    }

    /**
     * Gets the top N slowest operations by average execution time
     * @param n Number of operations to return
     * @return List of the slowest operations
     */
    public static List<ProfilerReport> getTopSlowest(int n) {
        List<ProfilerReport> allReports = getAllReports();
        allReports.sort((r1, r2) -> Double.compare(r2.getAverageMs(), r1.getAverageMs()));
        return allReports.subList(0, Math.min(n, allReports.size()));
    }

    /**
     * Gets the top N most frequently called operations
     * @param n Number of operations to return
     * @return List of the most frequent operations
     */
    public static List<ProfilerReport> getTopFrequent(int n) {
        List<ProfilerReport> allReports = getAllReports();
        allReports.sort((r1, r2) -> Long.compare(r2.getExecutionCount(), r1.getExecutionCount()));
        return allReports.subList(0, Math.min(n, allReports.size()));
    }

    /**
     * Resets profiling data for a specific operation
     * @param operationName The name of the operation to reset
     */
    public static void reset(String operationName) {
        profilerDataMap.remove(operationName);
    }

    /**
     * Resets all profiling data
     */
    public static void resetAll() {
        profilerDataMap.clear();
        threadLocalStartTimes.get().clear();
    }

    /**
     * Enables or disables the profiler
     * @param enable True to enable, false to disable
     */
    public static void setEnabled(boolean enable) {
        enabled = enable;
    }

    /**
     * Checks if the profiler is enabled
     * @return True if enabled, false otherwise
     */
    public static boolean isEnabled() {
        return enabled;
    }

    /**
     * Gets the number of tracked operations
     * @return Number of operations being profiled
     */
    public static int getTrackedOperationCount() {
        return profilerDataMap.size();
    }

    /**
     * Internal class to store profiling data for an operation
     */
    private static class ProfilerData {
        private final AtomicLong executionCount = new AtomicLong(0);
        private final AtomicLong totalTimeNanos = new AtomicLong(0);
        private volatile long minTimeNanos = Long.MAX_VALUE;
        private volatile long maxTimeNanos = Long.MIN_VALUE;
        private final Queue<Long> recentExecutions = new LinkedList<>();

        public synchronized void recordExecution(long durationNanos) {
            executionCount.incrementAndGet();
            totalTimeNanos.addAndGet(durationNanos);

            // Update min/max
            if (durationNanos < minTimeNanos) {
                minTimeNanos = durationNanos;
            }
            if (durationNanos > maxTimeNanos) {
                maxTimeNanos = durationNanos;
            }

            // Store recent executions
            recentExecutions.offer(durationNanos);
            if (recentExecutions.size() > MAX_STORED_OPERATIONS) {
                recentExecutions.poll();
            }
        }

        public ProfilerReport generateReport(String operationName) {
            long count = executionCount.get();
            if (count == 0) {
                return new ProfilerReport(operationName, 0, 0, 0, 0, 0);
            }

            double avgNanos = (double) totalTimeNanos.get() / count;
            double avgMs = avgNanos / 1_000_000.0;
            double minMs = minTimeNanos / 1_000_000.0;
            double maxMs = maxTimeNanos / 1_000_000.0;
            double totalMs = totalTimeNanos.get() / 1_000_000.0;

            return new ProfilerReport(operationName, count, avgMs, minMs, maxMs, totalMs);
        }
    }
}
