package com.ssomar.score.commands.runnable.player.events;

import com.ssomar.score.commands.runnable.player.commands.openchest.OpenChest;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public class OpenChestEvent implements Listener {

    private static final Boolean DEBUG = true;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryOpenEvent(InventoryOpenEvent e) {
        HumanEntity humanEntity = e.getPlayer();
        if(humanEntity instanceof Player){
            Player player = (Player) humanEntity;
            if(OpenChest.getInstance().getBypassChests().containsKey(player)){
                Inventory inv = OpenChest.getInstance().getBypassChests().get(player);
                if(inv.equals(e.getInventory())) {
                    //SsomarDev.testMsg("IS CANCEL OPEN CHEST ? " + e.isCancelled(), DEBUG);
                    e.setCancelled(false);
                    //SsomarDev.testMsg("CANCEL TURNED TO FALSE ", DEBUG);
                }
                OpenChest.getInstance().getBypassChests().remove(player);
            }
        }
    }
}
