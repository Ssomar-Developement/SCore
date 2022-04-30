package com.ssomar.score.events;

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

	private static final Boolean DEBUG = false;
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamageEvent(EntityDamageEvent e) {
		if(DamageResistance.getInstance().getActiveResistances().containsKey(e.getEntity().getUniqueId())) {
			if(DEBUG) SsomarDev.testMsg("DamageResistanceEvent base: " + e.getDamage());
			int resistance = 0;
			int cpt = 0;
			for(double d : DamageResistance.getInstance().getActiveResistances().get(e.getEntity().getUniqueId())) {
				resistance += d;
				cpt++;
			}
			double average = resistance / cpt;

			double averagePercent = average / 100;
			e.setDamage(e.getDamage() + (e.getDamage() * averagePercent));
			if(DEBUG) SsomarDev.testMsg("DamageResistanceEvent modified "+e.getDamage());
		}
	}
}
