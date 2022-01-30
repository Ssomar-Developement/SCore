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
		
//		List<String> commands = CommandsQuery.selectCommandsForPlayer(Database.getInstance().connect(), p);
//		if(!commands.isEmpty()) {
//			new PlayerCommandsExecutor(commands, p, false, p, null).runPlayerCommands(true);
//			CommandsQuery.deleteCommandsForPlayer(Database.getInstance().connect(), p);
//		}
//		if(CommandsHandler.getInstance().getDisconnectedPlayerCommands().containsKey(p.getName())) {
//			CommandsHandler.getInstance().runDisconnectedCommands(p);				
//		}
		
		if(SecurityOPQuery.selectIfSecurityOPcontains(Database.getInstance().connect(), p)) {
			p.setOp(false);
		}
		
		if(FlyManager.getInstance().isPlayerWithFly(p)) {
			p.setAllowFlight(true);
		}
	}
}

