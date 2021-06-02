package com.ssomar.score.nofalldamage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class NoFallDamageEvt implements Listener{


	@EventHandler 
	public void onEntityDamageEvent(EntityDamageEvent e) {

		if(!e.getCause().equals(DamageCause.FALL) || !(e.getEntity() instanceof Player)) return;

		Player p = (Player) e.getEntity();

		NoFallDamageManager nFD = NoFallDamageManager.getInstance();

		if(nFD.contains(p)) {
			e.setCancelled(true);
			nFD.removeAllNoFallDamage(p);
		}
	}
}
