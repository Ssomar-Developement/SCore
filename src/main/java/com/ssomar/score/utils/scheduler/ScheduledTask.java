package com.ssomar.score.utils.scheduler;

/**
 * Used to hold details of a Bukkit RunnableTask for SCore
 */
public interface ScheduledTask {

    void cancel();

    boolean isCancelled();

}