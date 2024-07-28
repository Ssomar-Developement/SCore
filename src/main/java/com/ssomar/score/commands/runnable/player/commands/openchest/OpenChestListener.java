package com.ssomar.score.commands.runnable.player.commands.openchest;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;


public class OpenChestListener implements Listener {

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e) {
        HumanEntity humanEntity = e.getPlayer();
        if(humanEntity instanceof Player) {
            Player player = (Player) humanEntity;
            OpenChestManager openChestManager = OpenChestManager.getInstance();
            openChestManager.removeForcedChunk(player.getUniqueId());
        }
    }
}