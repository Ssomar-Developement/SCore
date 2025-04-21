package com.ssomar.score.commands.runnable.block.commands.settempblock;

import com.ssomar.score.SCore;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

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

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                loc.getBlock().setBlockData(finalOldData);
                savedModifiedBlocks.remove(loc);
            }
        };
        SCore.schedulerHook.runLocationTask(runnable3, loc, time);
    }

    public static SetTempBlockManager getInstance() {
        if(instance == null) instance = new SetTempBlockManager();
        return instance;
    }
}
