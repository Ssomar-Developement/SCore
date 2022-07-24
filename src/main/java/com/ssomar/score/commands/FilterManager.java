package com.ssomar.score.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class FilterManager {

    private static FilterManager instance;

    private static int currentlyInRun = 0;

    public FilterManager() {
        Logger logger = ((Logger) LogManager.getRootLogger());
        logger.addFilter(new LogFilter());
    }

    public static FilterManager getInstance() {
        if (instance == null) instance = new FilterManager();
        return instance;
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
