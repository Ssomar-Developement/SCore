package com.ssomar.score.events;

import me.jet315.minions.events.MinerBlockBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class FixJetsMinionsBlockBreakEvent implements Listener {

    @EventHandler
    public void onMinionMine(MinerBlockBreakEvent e) {
        Block block = e.getBlock();
        UUID uuid = e.getMinion().getPlayerUUID();
        if(uuid == null) return;
        Player p = Bukkit.getPlayer(uuid);
        if (p != null && p.isOnline()) {
            BlockBreakEvent event = new BlockBreakEvent(block, p);
            Bukkit.getPluginManager().callEvent(event);
        }
    }
}
