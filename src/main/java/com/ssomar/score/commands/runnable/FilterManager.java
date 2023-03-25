package com.ssomar.score.commands.runnable;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class FilterManager {

    private static FilterManager instance;

    private static int currentlyInRun = 0;

    public FilterManager() {
        //SsomarDev.testMsg("FilterManager "+LogManager.ROOT_LOGGER_NAME, true);
        //LogManager.getRootLogger().always();
        Logger logger = ((Logger) LogManager.getRootLogger());
        logger.addFilter(new LogFilter());
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
        return currentlyInRun > 0;
    }

    public void incCurrentlyInRun() {
        currentlyInRun = currentlyInRun + 1;
    }

    public void decrCurrentlyInRun() {
        currentlyInRun = currentlyInRun - 1;
    }

    public int getCurrentlyInRun() {
        return currentlyInRun;
    }
}
