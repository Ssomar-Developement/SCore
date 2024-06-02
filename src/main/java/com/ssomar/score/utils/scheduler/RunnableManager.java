package com.ssomar.score.utils.scheduler;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class RunnableManager {

    private static RunnableManager instance;

    // Register current runnable, they will be executed when the server goes off
    private List<BukkitRunnable> tasks;

    public RunnableManager() {
        tasks = new java.util.ArrayList<>();
    }

    public void addTask(BukkitRunnable task) {
        tasks.add(task);
    }

    public void removeTask(BukkitRunnable task) {
        tasks.remove(task);
    }

    public static RunnableManager getInstance() {
        if (instance == null) instance = new RunnableManager();
        return instance;
    }

    public void forceRunTasks() {
        for (BukkitRunnable task : tasks) {
            task.run();
        }
    }
}
