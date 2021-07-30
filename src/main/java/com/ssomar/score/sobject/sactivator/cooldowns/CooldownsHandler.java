package com.ssomar.score.sobject.sactivator.cooldowns;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ssomar.score.data.CooldownsQuery;
import com.ssomar.score.data.Database;

public class CooldownsHandler implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerJoinEvent(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		List<Cooldown> cooldowns = CooldownsQuery.getCooldownsOf(Database.getInstance().connect(), p.getUniqueId());
		
		CooldownsManager.getInstance().addCooldowns(cooldowns);
		
		CooldownsQuery.deleteCooldownsOf(Database.getInstance().connect(), p.getUniqueId());
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerQuitEvent(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		List<Cooldown> cooldowns = CooldownsManager.getInstance().getCooldownsOf(p.getUniqueId());
		
		CooldownsQuery.insertCooldowns(Database.getInstance().connect(), cooldowns);
		
		CooldownsManager.getInstance().removeCooldownsOf(p.getUniqueId());
	}

}
