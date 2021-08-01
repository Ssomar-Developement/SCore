package com.ssomar.score.utils.placeholders;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TargetPlaceholders extends PlaceholdersInterface{
	
	/* placeholders of the target player */
	private UUID targetUUID;
	
	private String target = "";
	private String targetX = "";
	private String targetY = "";
	private String targetZ = "";
	private String targetXInt = "";
	private String targetYInt = "";
	private String targetZInt = "";	
	private String targetWorld = "";	
	private String targetSlot = "";

	public void setTargetPlcHldr(UUID uuid) {
		this.targetUUID = uuid;
		this.reloadTargetPlcHldr();
	}

	public void reloadTargetPlcHldr() {
		Player player;
		if(this.targetUUID != null && (player = Bukkit.getPlayer(targetUUID)) != null) {
			this.target = player.getName();
			Location pLoc = player.getLocation();
			this.targetX = pLoc.getX()+"";
			this.targetY = pLoc.getY()+"";
			this.targetZ = pLoc.getZ()+"";
			this.targetXInt = pLoc.getBlockX()+"";
			this.targetYInt = pLoc.getBlockY()+"";
			this.targetZInt = pLoc.getBlockZ()+"";
			this.targetWorld = pLoc.getWorld().getName();
			this.targetSlot = player.getInventory().getHeldItemSlot()+"";
		}
	}
	
	public String replacePlaceholder(String s) {
		if(targetUUID != null) {
			s = s.replaceAll("%target%", target);
			s = s.replaceAll("%target_uuid%", targetUUID.toString());
			s = replaceCalculPlaceholder(s, "%target_x%", targetX, false);
			s = replaceCalculPlaceholder(s, "%target_y%", targetY, false);
			s = replaceCalculPlaceholder(s, "%target_z%", targetZ, false);
			s = replaceCalculPlaceholder(s, "%target_x_int%", targetXInt, true);
			s = replaceCalculPlaceholder(s, "%target_y_int%", targetYInt, true);
			s = replaceCalculPlaceholder(s, "%target_z_int%", targetZInt, true);
			s = s.replaceAll("%target_world%", targetWorld);
			s = s.replaceAll("%target_slot%", targetSlot);
		}
		
		return s;
	}
}
