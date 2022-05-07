package com.ssomar.score.commands.runnable.player.events;

import com.sk89q.worldguard.bukkit.event.entity.DamageEntityEvent;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.player.commands.DamageResistance;
import com.ssomar.score.commands.runnable.player.commands.XpBoost;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.util.UUID;

public class DamageResistanceEvent implements Listener{

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageEvent(EntityDamageEvent e) {
		e.setDamage(DamageResistance.getInstance().getNewDamage(e.getEntity().getUniqueId(), e.getDamage()));
	}
}
