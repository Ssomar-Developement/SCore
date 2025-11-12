package com.ssomar.score.variables;

import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.data.Database;
import com.ssomar.score.utils.logging.Utils;
import com.ssomar.score.variables.manager.VariablesManager;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Task that periodically syncs variables with MySQL to ensure all servers stay synchronized
 * This handles cases where direct updates might be missed due to network issues or timing
 */
public class MySQLSyncTask extends BukkitRunnable {

    private static MySQLSyncTask instance;
    private static final long DEFAULT_SYNC_INTERVAL_SECONDS = 60L;
    private long syncIntervalTicks;
    private boolean isRunning = false;

    private MySQLSyncTask() {
        // Private constructor for singleton
    }

    public static MySQLSyncTask getInstance() {
        if (instance == null) {
            instance = new MySQLSyncTask();
        }
        return instance;
    }

    /**
     * Start the periodic sync task with configurable interval
     */
    public void start() {
        start(DEFAULT_SYNC_INTERVAL_SECONDS);
    }

    /**
     * Start the periodic sync task with specified interval
     * @param intervalSeconds Sync interval in seconds
     */
    public void start(long intervalSeconds) {
        if (isRunning) {
            return; // Already running
        }

        if (!GeneralConfig.getInstance().isUseMySQL()) {
            return; // MySQL not enabled
        }

        this.syncIntervalTicks = 20L * intervalSeconds;
        isRunning = true;
        this.runTaskTimerAsynchronously(SCore.plugin, syncIntervalTicks, syncIntervalTicks);
        Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7MySQL variable sync task started (syncing every " + intervalSeconds + " seconds)");
    }

    /**
     * Stop the sync task
     */
    public void stop() {
        if (isRunning) {
            this.cancel();
            isRunning = false;
            Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7MySQL variable sync task stopped");
        }
    }

    @Override
    public void run() {
        if (!GeneralConfig.getInstance().isUseMySQL()) {
            stop(); // Stop if MySQL was disabled
            return;
        }

        try {
            // Import all variables from MySQL to get updates from other servers
            VariablesManager.getInstance().updateAllLoadedMySQL(VariablesManager.MODE.IMPORT);

            if (Database.DEBUG) {
                Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7MySQL variables synchronized successfully");
            }
        } catch (Exception e) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR + " &cError during MySQL variable sync: " + e.getMessage());
            if (Database.DEBUG) {
                e.printStackTrace();
            }
        }
    }
}