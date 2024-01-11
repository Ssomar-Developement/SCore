package com.ssomar.score.utils.scheduler;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface SchedulerHook {

    ScheduledTask runTask(Runnable runnable, long delay);

    ScheduledTask runRepeatingTask(Runnable runnable, long initDelay, long period);

    ScheduledTask runAsyncTask(Runnable runnable, long delay);

    ScheduledTask runAsyncRepeatingTask(Runnable runnable, long initDelay, long period);
    ScheduledTask runEntityTask(Runnable runnable, Runnable retired, Entity entity, long delay);

    ScheduledTask runEntityTaskAsap(Runnable runnable, Runnable retired, Entity entity);

    ScheduledTask runLocationTask(Runnable runnable, Location location, long delay);

    ScheduledTask runLocationTaskAsap(Runnable runnable, Location location);

}
