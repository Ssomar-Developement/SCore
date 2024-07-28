package com.ssomar.score.utils.scheduler;



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
        for (Runnable task : tasks) {
            task.run();
        }
    }
}
