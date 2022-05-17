package com.ssomar.score.commands.runnable.player.events;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.player.commands.DamageBoost;
import com.ssomar.score.commands.runnable.player.commands.DamageResistance;
import com.ssomar.score.utils.NTools;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageBoostEvent implements Listener{

	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamageEvent(EntityDamageByEntityEvent e) {
		e.setDamage(NTools.reduceDouble(DamageBoost.getInstance().getNewDamage(e.getDamager().getUniqueId(), e.getDamage()),2));
	}
}
