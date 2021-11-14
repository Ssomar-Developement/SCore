package com.ssomar.score.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import com.ssomar.score.commands.runnable.CommandsHandler;

public class PlayerPickup implements Listener {

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void PlayerPickupItemEvent(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(CommandsHandler.getInstance().hasStopPickup(p)) e.setCancelled(true);
		}
	}
}
