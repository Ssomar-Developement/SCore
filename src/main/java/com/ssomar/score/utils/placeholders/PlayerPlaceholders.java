package com.ssomar.score.utils.placeholders;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerPlaceholders extends PlaceholdersInterface{
	
	/* placeholders of the player */
	private UUID playerUUID;
	
	private String player = "";
	private String x = "";
	private String y = "";
	private String z = "";
	private String xInt = "";
	private String yInt = "";
	private String zInt = "";
	private String world = "";
	private String slot = "";
	
	public void setPlayerPlcHldr(UUID uuid) {
		this.playerUUID = uuid;
		this.reloadPlayerPlcHldr();
	}

	public void reloadPlayerPlcHldr() {
		Player player;
		if(this.playerUUID != null && (player = Bukkit.getPlayer(playerUUID)) != null) {
			this.player = player.getName();
			this.playerUUID = player.getUniqueId();
			Location pLoc = player.getLocation();
			this.x = pLoc.getX()+"";
			this.y = pLoc.getY()+"";
			this.z = pLoc.getZ()+"";
			this.xInt = pLoc.getBlockX()+"";
			this.yInt = pLoc.getBlockY()+"";
			this.zInt = pLoc.getBlockZ()+"";
			this.world = pLoc.getWorld().getName();
			this.slot = player.getInventory().getHeldItemSlot()+"";
		}
	}
	
	public String replacePlaceholder(String s) {
		if(playerUUID != null) {
			s = s.replaceAll("%player%", player);
			s = s.replaceAll("%player_uuid%", playerUUID.toString());
			s = replaceCalculPlaceholder(s, "%x%", x, false);
			s = replaceCalculPlaceholder(s, "%y%", y, false);
			s = replaceCalculPlaceholder(s, "%z%", z, false);
			s = replaceCalculPlaceholder(s, "%x_int%", xInt, true);
			s = replaceCalculPlaceholder(s, "%y_int%", yInt, true);
			s = replaceCalculPlaceholder(s, "%z_int%", zInt, true);
			s = s.replaceAll("%world%", world);
			s = s.replaceAll("%slot%", slot);
		}
		
		return s;
	}

	public UUID getPlayerUUID() {
		return playerUUID;
	}

	public void setPlayerUUID(UUID playerUUID) {
		this.playerUUID = playerUUID;
	}

}
