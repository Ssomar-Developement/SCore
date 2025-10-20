package com.ssomar.score.utils.profiler;

/**
 * Represents a performance profiling report for a specific operation.
 * Contains statistics about execution times and frequency.
 */
public class ProfilerReport {

    private final String operationName;
    private final long executionCount;
    private final double averageMs;
    private final double minMs;
    private final double maxMs;
    private final double totalMs;

    public ProfilerReport(String operationName, long executionCount, double averageMs,
                         double minMs, double maxMs, double totalMs) {
        this.operationName = operationName;
        this.executionCount = executionCount;
        this.averageMs = averageMs;
        this.minMs = minMs;
        this.maxMs = maxMs;
        this.totalMs = totalMs;
    }

    /**
     * Gets the name of the profiled operation
     * @return The operation name
     */
    public String getOperationName() {
        return operationName;
    }

    /**
     * Gets the total number of times this operation was executed
     * @return The execution count
     */
    public long getExecutionCount() {
        return executionCount;
    }

    /**
     * Gets the average execution time in milliseconds
     * @return Average execution time in ms
     */
    public double getAverageMs() {
        return averageMs;
    }

    /**
     * Gets the minimum execution time in milliseconds
     * @return Minimum execution time in ms
     */
    public double getMinMs() {
        return minMs;
    }

    /**
     * Gets the maximum execution time in milliseconds
     * @return Maximum execution time in ms
     */
    public double getMaxMs() {
        return maxMs;
    }

    /**
     * Gets the total time spent executing this operation in milliseconds
     * @return Total execution time in ms
     */
    public double getTotalMs() {
        return totalMs;
    }

    /**
     * Formats the report as a human-readable string
     * @return Formatted report string
     */
    public String toFormattedString() {
        return String.format(
            "Operation: %s\n" +
            "  Executions: %,d\n" +
            "  Average: %.3f ms\n" +
            "  Min: %.3f ms\n" +
            "  Max: %.3f ms\n" +
            "  Total: %.3f ms",
            operationName, executionCount, averageMs, minMs, maxMs, totalMs
        );
    }

    /**
     * Formats the report as a compact single line
     * @return Compact report string
     */
    public String toCompactString() {
        return String.format(
            "%s: %,d exec, avg=%.3fms, min=%.3fms, max=%.3fms, total=%.3fms",
            operationName, executionCount, averageMs, minMs, maxMs, totalMs
        );
    }

    @Override
    public String toString() {
        return toFormattedString();
    }
}
