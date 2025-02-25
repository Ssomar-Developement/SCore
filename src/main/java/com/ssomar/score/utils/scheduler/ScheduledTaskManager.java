package com.ssomar.score.utils.scheduler;


import java.util.ArrayList;
import java.util.List;

public class ScheduledTaskManager {

    private static ScheduledTaskManager instance;

    // Register current runnable, they will be executed when the server goes off
    private List<ScheduledTask> tasks;

    public ScheduledTaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(ScheduledTask task) {
        tasks.add(task);
    }

    public void removeTask(ScheduledTask task) {
        tasks.remove(task);
    }

    public static ScheduledTaskManager getInstance() {
        if (instance == null) instance = new ScheduledTaskManager();
        return instance;
    }

    public void cancelScheduledTask() {
        // Create a copy of the tasks list to iterate over
        List<ScheduledTask> tasksCopy = new ArrayList<>(tasks);

        for (ScheduledTask task : tasksCopy) {
            task.cancel();
        }

        tasks.clear();
    }
}
