package com.ssomar.score.utils.placeholders;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerPlaceholders extends PlaceholdersInterface{
	
	/* placeholders of the player */
	private UUID playerUUID;
	private int fixSlot;
	
	private String player = "";
	private String x = "";
	private String y = "";
	private String z = "";
	private String xInt = "";
	private String yInt = "";
	private String zInt = "";
	private String world = "";
	private String slot = "";
	private String slotLive = "";
	
	public void setPlayerPlcHldr(UUID uuid) {
		this.playerUUID = uuid;
		this.reloadPlayerPlcHldr();
	}
	
	public void setPlayerPlcHldr(UUID uuid, int fixSlot) {
		this.playerUUID = uuid;
		this.fixSlot = fixSlot;
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
			if(fixSlot != -1) this.slot = fixSlot+"";
			else this.slot = player.getInventory().getHeldItemSlot()+"";
			this.slotLive = player.getInventory().getHeldItemSlot()+"";
		}
	}
	
	public String replacePlaceholder(String s) {
		String toReplace = s;
		if(playerUUID != null) {
			toReplace = toReplace.replaceAll("%player%", player);
			toReplace = toReplace.replaceAll("%player_uuid%", playerUUID.toString());
			toReplace = replaceCalculPlaceholder(toReplace, "%x%", x, false);
			toReplace = replaceCalculPlaceholder(toReplace, "%y%", y, false);
			toReplace = replaceCalculPlaceholder(toReplace, "%z%", z, false);
			toReplace = replaceCalculPlaceholder(toReplace, "%x_int%", xInt, true);
			toReplace = replaceCalculPlaceholder(toReplace, "%y_int%", yInt, true);
			toReplace = replaceCalculPlaceholder(toReplace, "%z_int%", zInt, true);
			toReplace = toReplace.replaceAll("%world%", world);
			toReplace = toReplace.replaceAll("%slot%", slot);
			toReplace = toReplace.replaceAll("%slot_live%", slotLive);
		}
		
		return toReplace;
	}

	public UUID getPlayerUUID() {
		return playerUUID;
	}

	public void setPlayerUUID(UUID playerUUID) {
		this.playerUUID = playerUUID;
	}

}
