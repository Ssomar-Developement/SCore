package com.ssomar.score.commands.runnable;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.scheduler.BukkitRunnable;

public class FilterManager {

    private static FilterManager instance;
    private static LogFilter logFilter;

    private static int CurrentlyInRun = 0;

    public FilterManager() {
        //SsomarDev.testMsg("FilterManager "+LogManager.ROOT_LOGGER_NAME, true);
        //LogManager.getRootLogger().always();
        logFilter = new LogFilter();
        Logger logger = ((Logger) LogManager.getRootLogger());
        logger.addFilter(logFilter);

        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                setLogFilterPrior();
            }
        };
        //r.runTaskLater(SCore.plugin, 100);
        SCore.schedulerHook.runTask(r, 100);

    }

    public void reload(){
        logFilter.reload();
    }

    public static FilterManager getInstance() {
        if(instance == null) instance = new FilterManager();
        return instance;
    }

    public void showDebug() {
        Logger logger = ((Logger) LogManager.getRootLogger());
        logger.getFilters().forEachRemaining(f -> SsomarDev.testMsg(">> FilterManager "+f.toString(), true));
    }

    public void setLogFilterPrior(){
        /* No need in below 1.12 ItemsAdder that takes the priority and cause the issue is not in this version */
        if(SCore.is1v12Less()) return;
        Logger logger = ((Logger) LogManager.getRootLogger());
        logger.getFilters().forEachRemaining(f -> {
            if(!(f instanceof LogFilter)){
                logger.get().removeFilter(f);
                logger.addFilter(f);
            }
        });
    }

    public boolean isSilenceOuput() {
        //System.out.println("isSilenceOuput "+CurrentlyInRun);
        return CurrentlyInRun > 0;
    }

    public void incCurrentlyInRun() {
        CurrentlyInRun = CurrentlyInRun + 1;
    }

    public void decrCurrentlyInRun() {
        CurrentlyInRun = CurrentlyInRun - 1;
    }

    public int getCurrentlyInRun() {
        return CurrentlyInRun;
    }
}
