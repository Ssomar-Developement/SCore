package com.ssomar.score.commands.runnable;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.ssomar.score.SCore;

public class SecurityOPCommands implements Listener{

	@EventHandler
	public void onPerformCommandAsOP(PlayerCommandPreprocessEvent e){

		Player p = e.getPlayer();
		if(SUDOOPManager.getInstance().getCommandsAsOP().containsKey(p)) {
			List<String> commands = SUDOOPManager.getInstance().getCommandsAsOP().get(p);
			if(!commands.contains(e.getMessage())) {
				Bukkit.getLogger().severe(SCore.NAME_2+" WARNING THE COMMAND "+e.getMessage()+" HAS BEEN BLOCKED WHEN SUDOOP "+p.getName()+ " PROBABLY USE HACKED CLIENT");
				e.setCancelled(true);
			}
		}
	}
}
