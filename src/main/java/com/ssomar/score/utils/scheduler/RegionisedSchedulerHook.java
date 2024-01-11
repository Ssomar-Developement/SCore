package com.ssomar.score.utils.scheduler;

import com.ssomar.score.SCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.concurrent.TimeUnit;

public class RegionisedSchedulerHook implements SchedulerHook {
    private final SCore SCore;

    public RegionisedSchedulerHook(SCore SCore) {
        this.SCore = SCore;
    }

    @Override
    public ScheduledTask runTask(Runnable runnable, long delay) {
        if(delay > 0)
            return new RegionisedScheduledTask(Bukkit.getGlobalRegionScheduler().runDelayed(SCore, task -> runnable.run(), delay));
        else
            return new RegionisedScheduledTask(Bukkit.getGlobalRegionScheduler().run(SCore, task -> runnable.run()));
    }


    @Override
    public ScheduledTask runRepeatingTask(Runnable runnable, long initDelay, long period) {
        if(initDelay <= 0) initDelay = 1;
        return new RegionisedScheduledTask(Bukkit.getGlobalRegionScheduler().runAtFixedRate(SCore, task -> runnable.run(), initDelay, period));
    }

    @Override
    public ScheduledTask runAsyncTask(Runnable runnable, long delay) {
        // convert tick to ms
        delay *= 50;
        if(delay > 0)
            return new RegionisedScheduledTask(Bukkit.getAsyncScheduler().runDelayed(SCore, task -> runnable.run(), delay, TimeUnit.MILLISECONDS));
        else
            return new RegionisedScheduledTask(Bukkit.getAsyncScheduler().runNow(SCore, task -> runnable.run()));
    }

    @Override
    public ScheduledTask runAsyncRepeatingTask(Runnable runnable, long initDelay, long period) {
        // convert tick to ms
        initDelay *= 50;
        period *= 50;
        if(initDelay <= 0) initDelay = 1;
        return new RegionisedScheduledTask(Bukkit.getAsyncScheduler().runAtFixedRate(SCore, task -> runnable.run(), initDelay, period, TimeUnit.MILLISECONDS));
    }

    @Override
    public ScheduledTask runEntityTask(Runnable runnable, Runnable retired, Entity entity, long delay) {
        io.papermc.paper.threadedregions.scheduler.ScheduledTask scheduledTask = null;
        if(delay > 0)
            scheduledTask = entity.getScheduler().runDelayed(SCore, task -> runnable.run(), retired, delay);
        else scheduledTask = entity.getScheduler().run(SCore, task -> runnable.run(), retired);
        return scheduledTask == null ? null : new RegionisedScheduledTask(scheduledTask);
    }

    @Override
    public ScheduledTask runEntityTaskAsap(Runnable runnable, Runnable retired, Entity entity) {
        if (Bukkit.isOwnedByCurrentRegion(entity)) {
            runnable.run();
            return new ScheduledTask() {
                @Override
                public void cancel() {}

                @Override
                public boolean isCancelled() {
                    return false;
                }
            };
        }
        return runEntityTask(runnable, retired, entity, 0);
    }

    @Override
    public ScheduledTask runLocationTask(Runnable runnable,Location location, long delay) {
        io.papermc.paper.threadedregions.scheduler.ScheduledTask scheduledTask = null;
        if(delay > 0)
            scheduledTask = Bukkit.getRegionScheduler().runDelayed(SCore, location, task -> runnable.run(), delay);
        else scheduledTask = Bukkit.getRegionScheduler().run(SCore, location, task -> runnable.run());
        return scheduledTask == null ? null : new RegionisedScheduledTask(scheduledTask);
    }

    @Override
    public ScheduledTask runLocationTaskAsap(Runnable runnable, Location location) {
        if (Bukkit.isOwnedByCurrentRegion(location)) {
            runnable.run();
            return new ScheduledTask() {
                @Override
                public void cancel() {}

                @Override
                public boolean isCancelled() {
                    return false;
                }
            };
        }
        return runLocationTask(runnable, location, 0);
    }

    public static boolean isCompatible() {
        try {
            Class.forName("io.papermc.paper.threadedregions.scheduler.AsyncScheduler");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    private static class RegionisedScheduledTask implements ScheduledTask {
        private final io.papermc.paper.threadedregions.scheduler.ScheduledTask scheduledTask;

        private RegionisedScheduledTask(io.papermc.paper.threadedregions.scheduler.ScheduledTask scheduledTask) {
            this.scheduledTask = scheduledTask;
        }

        @Override
        public void cancel() {
            scheduledTask.cancel();
        }

        @Override
        public boolean isCancelled() {
            return scheduledTask.isCancelled();
        }
    }

}
