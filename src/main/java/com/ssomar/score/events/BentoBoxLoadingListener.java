package com.ssomar.score.events;

import com.ssomar.executableblocks.ExecutableBlocks;
import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlocksPlacedManager;
import com.ssomar.score.SCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import world.bentobox.bentobox.api.events.BentoBoxReadyEvent;

public class BentoBoxLoadingListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void bentoBoxReady(BentoBoxReadyEvent e) {

        // I dont know why I coded that, probably for that https://discord.com/channels/701066025516531753/923210141413818408/1010622644384702567
        BukkitRunnable runnable3 = new BukkitRunnable() {
            @Override
            public void run() {
                if(SCore.hasExecutableBlocks){
                    ExecutableBlocks.plugin.onReload(false);
                    ExecutableBlocksPlacedManager.getInstance().load();
                }
            }
        };
        SCore.schedulerHook.runTask(runnable3, 100);
    }
}
