package com.ssomar.score.events;

import com.ssomar.score.commands.runnable.CommandsHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PlayerPickupListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void PlayerPickupItemEvent(EntityPickupItemEvent e) {

        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (CommandsHandler.getInstance().hasStopPickup(p) || CommandsHandler.getInstance().hasStopPickup(p, e.getItem().getItemStack().getType())) e.setCancelled(true);
        }
    }
}
