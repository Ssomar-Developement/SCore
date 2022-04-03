package com.ssomar.score.events;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.block.commands.SilkSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ssomar.score.data.Database;
import com.ssomar.score.data.SecurityOPQuery;
import com.ssomar.score.fly.FlyManager;

public class PlayerReconnexion implements Listener {
	
	
	@EventHandler(priority = EventPriority.HIGH)
	public void playerReconnexion(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if(SecurityOPQuery.selectIfSecurityOPcontains(Database.getInstance().connect(), p)) {
			p.setOp(false);
			SecurityOPQuery.deletePlayerOP(Database.getInstance().connect(), p);
		}
		
		if(FlyManager.getInstance().isPlayerWithFly(p)) {
			p.setAllowFlight(true);
		}
	}
}

