package com.ssomar.score.events;

import com.comphenix.protocol.ProtocolLogger;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;

import com.ssomar.score.commands.runnable.CommandsHandler;
import org.bukkit.event.entity.EntityPoseChangeEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerPickup implements Listener {

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void PlayerPickupItemEvent(EntityPickupItemEvent e) {

		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(CommandsHandler.getInstance().hasStopPickup(p)) e.setCancelled(true);
		}
	}
}
