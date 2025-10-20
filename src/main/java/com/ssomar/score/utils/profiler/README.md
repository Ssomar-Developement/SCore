# Performance Profiler

A powerful utility for tracking and analyzing the performance of operations in SCore and its dependent plugins.

## Overview

The Performance Profiler helps identify performance bottlenecks by tracking execution times of commands, features, events, and any custom operations. It provides detailed statistics including average, minimum, maximum, and total execution times.

## Features

- **Thread-safe profiling** - Safe to use in multi-threaded environments
- **Minimal overhead** - Uses nano-second precision with minimal performance impact
- **Automatic tracking** - Store up to 10,000 executions per operation
- **Real-time statistics** - Get instant reports on operation performance
- **Flexible reporting** - View individual, top slowest, most frequent, or all operations
- **Enable/Disable** - Toggle profiler on/off without restarting the server

## Usage

### In Code

```java
import com.ssomar.score.utils.profiler.PerformanceProfiler;

// Simple start/stop profiling
PerformanceProfiler.start("myOperation");
// ... your code ...
PerformanceProfiler.stop("myOperation");

// Or use the profile method with a Runnable
PerformanceProfiler.profile("myOperation", () -> {
    // ... your code ...
});

// Get a report
ProfilerReport report = PerformanceProfiler.getReport("myOperation");
if (report != null) {
    System.out.println(report.toFormattedString());
}
```

### In Game Commands

All commands require the permission `score.cmd.profiler` or `score.cmds` or `score.*`.

#### Enable/Disable Profiler
```
/score profiler enable
/score profiler disable
```

#### View Profiler Status
```
/score profiler status
```

#### View All Profiled Operations
```
/score profiler all
```

#### View Detailed Report for Specific Operation
```
/score profiler report <operation>
```
Example: `/score profiler report DAMAGE_ENTITY`

#### View Top N Slowest Operations
```
/score profiler top [n]
```
Example: `/score profiler top 5` (defaults to 10 if n is not specified)

#### View Top N Most Frequent Operations
```
/score profiler frequent [n]
```
Example: `/score profiler frequent 20` (defaults to 10 if n is not specified)

#### Reset Profiler Data
```
/score profiler reset [operation]
```
- Reset specific operation: `/score profiler reset DAMAGE_ENTITY`
- Reset all data: `/score profiler reset`

## Example Output

### Detailed Report
```
Operation: DAMAGE_ENTITY
  Executions: 1,542
  Average: 0.234 ms
  Min: 0.012 ms
  Max: 15.678 ms
  Total: 360.828 ms
```

### Top Slowest Operations
```
Top 5 slowest operations:
1. COMPLEX_CALCULATION - avg: 12.456ms (executions: 45)
2. DATABASE_QUERY - avg: 5.234ms (executions: 128)
3. FILE_IO_OPERATION - avg: 2.345ms (executions: 89)
4. NETWORK_REQUEST - avg: 1.234ms (executions: 67)
5. CACHE_LOOKUP - avg: 0.567ms (executions: 1,234)
```

## Best Practices

1. **Name operations clearly** - Use descriptive names like "PLAYER_DAMAGE_EVENT" instead of "event1"
2. **Profile at the right level** - Don't profile individual lines, profile logical operations
3. **Reset periodically** - Use `/score profiler reset` to clear old data during testing
4. **Disable in production** - For best performance, disable the profiler when not actively debugging
5. **Check frequently called operations** - Use `/score profiler frequent` to identify operations that run most often

## Technical Details

- **Storage**: Up to 10,000 executions per operation are stored
- **Precision**: Nano-second precision using `System.nanoTime()`
- **Thread Safety**: Uses `ConcurrentHashMap` and `ThreadLocal` for thread safety
- **Memory**: Automatically limits stored operations to prevent memory issues
- **Performance**: Minimal overhead when enabled (~10-50 nanoseconds per start/stop)

## Integration Examples

### Profiling Custom Commands
```java
public class MyCustomCommand extends PlayerCommand {
    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        PerformanceProfiler.start("MyCustomCommand");
        try {
            // Your command logic here
        } finally {
            PerformanceProfiler.stop("MyCustomCommand");
        }
    }
}
```

### Profiling Event Handlers
```java
@EventHandler
public void onPlayerDamage(EntityDamageByEntityEvent event) {
    PerformanceProfiler.profile("PlayerDamageEvent", () -> {
        // Your event handling logic
    });
}
```

### Profiling Features
```java
public void processFeature(FeatureData data) {
    String profilerName = "Feature_" + data.getFeatureType();
    PerformanceProfiler.start(profilerName);
    try {
        // Feature processing logic
    } finally {
        PerformanceProfiler.stop(profilerName);
    }
}
```

## Future Enhancements

Potential future improvements:
- Export reports to file (CSV, JSON)
- Web-based dashboard for viewing profiler data
- Automatic alerting when operations exceed thresholds
- Historical data tracking
- Comparison between different time periods
