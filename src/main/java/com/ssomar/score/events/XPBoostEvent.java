package com.ssomar.score.events;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.player.commands.XpBoost;
import com.ssomar.score.fly.FlyManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class XPBoostEvent implements Listener{

	private static final Boolean DEBUG = false;
	
	@EventHandler(priority = EventPriority.HIGH)
	public void playerExpChangeEvent(PlayerExpChangeEvent e) {
		Player player = e.getPlayer();
		if(DEBUG) SsomarDev.testMsg("XPBoostEvent");
		if(XpBoost.getInstance().getActiveBoosts().containsKey(player.getUniqueId())) {
			if(DEBUG) SsomarDev.testMsg("XPBoostEvent base: " + e.getAmount());
			if(DEBUG) SsomarDev.testMsg("XPBoostEvent modified "+(int)(e.getAmount() * XpBoost.getInstance().getActiveBoosts().get(player.getUniqueId())));
			e.setAmount((int)(e.getAmount() * XpBoost.getInstance().getActiveBoosts().get(player.getUniqueId())));
		}
	}
}
