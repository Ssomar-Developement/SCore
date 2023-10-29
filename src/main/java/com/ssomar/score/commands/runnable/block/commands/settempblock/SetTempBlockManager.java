package com.ssomar.score.commands.runnable.block.commands.settempblock;

import com.ssomar.score.SCore;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class SetTempBlockManager {

    public static SetTempBlockManager instance;
    private Map<Location, BlockData> savedModifiedBlocks;

    public SetTempBlockManager() {
        instance = this;
        savedModifiedBlocks = new HashMap<>();
    }

    public void runInitTempBlock(Location loc, BlockData oldData, int time) {

        if(!savedModifiedBlocks.containsKey(loc)) {
            savedModifiedBlocks.put(loc, oldData);
        }
        else oldData = savedModifiedBlocks.get(loc);

        final BlockData finalOldData = oldData;

        BukkitRunnable runnable3 = new BukkitRunnable() {
            @Override
            public void run() {
                loc.getBlock().setBlockData(finalOldData);
                savedModifiedBlocks.remove(loc);
            }
        };
        SCore.schedulerHook.runTask(runnable3, time);
    }

    public static SetTempBlockManager getInstance() {
        if(instance == null) instance = new SetTempBlockManager();
        return instance;
    }
}
