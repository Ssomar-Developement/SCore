package com.ssomar.score.utils.placeholders;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TargetPlaceholders extends PlaceholdersInterface implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* placeholders of the target player */
	private UUID targetUUID;
	
	private String target = "";
	private double targetX;
	private double targetY;
	private double targetZ;
	private String targetWorld = "";	
	private String targetSlot = "";
	private double targetLastDamageTaken;

	public void setTargetPlcHldr(UUID uuid) {
		this.targetUUID = uuid;
		this.reloadTargetPlcHldr();
	}

	public void reloadTargetPlcHldr() {
		Player player;
		if(this.targetUUID != null && (player = Bukkit.getPlayer(targetUUID)) != null) {
			this.target = player.getName();
			Location pLoc = player.getLocation();
			this.targetX = pLoc.getX();
			this.targetY = pLoc.getY();
			this.targetZ = pLoc.getZ();
			this.targetWorld = pLoc.getWorld().getName();
			this.targetSlot = player.getInventory().getHeldItemSlot()+"";
			this.targetLastDamageTaken = player.getLastDamage();
		}
	}
	
	public String replacePlaceholder(String s) {
		String toReplace = s;
		if(targetUUID != null) {
			toReplace = toReplace.replaceAll("%target%", target);
			toReplace = toReplace.replaceAll("%target_uuid%", targetUUID.toString());
			toReplace = replaceCalculPlaceholder(toReplace, "%target_x%", targetX+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_y%", targetY+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_z%", targetZ+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_x_int%", ((int) targetX)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_y_int%", ((int) targetY)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_z_int%", ((int) targetZ)+"", true);
			toReplace = toReplace.replaceAll("%target_world%", targetWorld);
			toReplace = toReplace.replaceAll("%target_slot%", targetSlot);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_last_damage_taken%", targetLastDamageTaken+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%target_last_damage_taken_int%", ((int) targetLastDamageTaken)+"", true);
		}
		
		return toReplace;
	}
}
