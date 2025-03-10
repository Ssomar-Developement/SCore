package com.ssomar.score.utils.scheduler;


import java.util.ArrayList;
import java.util.List;

public class RunnableManager {

    private static RunnableManager instance;

    // Register current runnable, they will be executed when the server goes off
    private List<Runnable> tasks;

    public RunnableManager() {
        tasks = new java.util.ArrayList<>();
    }

    public void addTask(Runnable task) {
        tasks.add(task);
    }

    public void removeTask(Runnable task) {
        tasks.remove(task);
    }

    public static RunnableManager getInstance() {
        if (instance == null) instance = new RunnableManager();
        return instance;
    }

    public void forceRunTasks() {
        // Create a copy of the tasks list to iterate over
        List<Runnable> tasksCopy = new ArrayList<>(tasks);

        for (Runnable task : tasksCopy) {
            task.run();
        }

        tasks.clear();
    }
}
