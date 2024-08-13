package com.ssomar.score.scheduler;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.utils.scheduler.ScheduledTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomTriggerScheduler {

    private static CustomTriggerScheduler instance;

    private Map<String, Runnable> tasksToRun;
    private Map<String, ScheduleFeatures> features;
    private Map<String, List<ScheduledTask>> tasks;

    private long lastCalculationTime;

    private CustomTriggerScheduler() {
        this.lastCalculationTime = System.currentTimeMillis();
        tasksToRun = new HashMap<>();
        features = new HashMap<>();
        tasks = new HashMap<>();

        SCore.schedulerHook.runAsyncRepeatingTask(this::runCalculations, 45, 20*120);
    }

    public static CustomTriggerScheduler getInstance() {
        if (instance == null) {
            instance = new CustomTriggerScheduler();
        }
        return instance;
    }

    public void addCustomTriggerScheduler(String id, Runnable task, ScheduleFeatures features) {
        this.tasks.put(id, new ArrayList<>());
        this.tasksToRun.put(id, task);
        this.features.put(id, features);
        runCalculation(id);
    }

    public void runCalculations() {
        this.lastCalculationTime = System.currentTimeMillis();
        for (String id : tasksToRun.keySet()) {
            SsomarDev.testMsg("Running calculation for: " + id, true);
            runCalculation(id);
        }
    }

    public void runCalculation(String id) {
        if(tasksToRun.containsKey(id)) {
            List<ScheduledTask> tasks = this.tasks.get(id);
            for (ScheduledTask task1 : tasks) {
                task1.cancel();
            }
            tasks.clear();
        }
        long time = 120 - (System.currentTimeMillis() - lastCalculationTime);
        List<Long> timestamps = features.get(id).getNextTimestamp(time * 1000);
        for (Long timestamp : timestamps) {
            long delayInTicks = (timestamp - System.currentTimeMillis()) / 50;
            SsomarDev.testMsg("Running task for " + id + " in " + delayInTicks + " ticks", true);
            ScheduledTask task = SCore.schedulerHook.runAsyncTask(tasksToRun.get(id), delayInTicks);
            tasks.computeIfAbsent(id, k -> new ArrayList<>()).add(task);
        }
    }
}
