package com.ssomar.score.utils.scheduler;
public interface ScheduledTask {

    void cancel();

    boolean isCancelled();

}