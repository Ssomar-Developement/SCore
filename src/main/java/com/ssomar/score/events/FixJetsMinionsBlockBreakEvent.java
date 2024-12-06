package com.ssomar.score.events;

import com.ssomar.score.utils.events.BlockBreakNoPlayerEvent;
import me.jet315.minions.events.MinerBlockBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FixJetsMinionsBlockBreakEvent implements Listener {


    @EventHandler
    public void onMinionMine(MinerBlockBreakEvent e) {
        Block block = e.getBlock();
        BlockBreakNoPlayerEvent event = new BlockBreakNoPlayerEvent(block);
        Bukkit.getPluginManager().callEvent(event);
    }
}
