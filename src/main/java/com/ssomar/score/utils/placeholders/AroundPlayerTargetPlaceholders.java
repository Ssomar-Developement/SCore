package com.ssomar.score.utils.placeholders;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AroundPlayerTargetPlaceholders extends PlaceholdersInterface implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* placeholders of the player */
	private UUID targetPlayerUUID;
	
	private String aroundTarget = "";
	private double aroundTargetX;
	private double aroundTargetY;
	private double aroundTargetZ;
	private String aroundTargetWorld = "";
	private String aroundTargetSlot = "";
	private double aroundTargetLastDamageTaken;

	
	public void setAroundPlayerTargetPlcHldr(UUID uuid) {
		this.targetPlayerUUID = uuid;
		this.reloadAroundPlayerTargetPlcHldr();
	}

	public void reloadAroundPlayerTargetPlcHldr() {
		Player player;
		if(this.targetPlayerUUID != null && (player = Bukkit.getPlayer(targetPlayerUUID)) != null) {
			this.aroundTarget = player.getName();
			Location pLoc = player.getLocation();
			this.aroundTargetX = pLoc.getX();
			this.aroundTargetY = pLoc.getY();
			this.aroundTargetZ = pLoc.getZ();
			this.aroundTargetWorld = pLoc.getWorld().getName();
			this.aroundTargetSlot = player.getInventory().getHeldItemSlot()+"";
			this.aroundTargetLastDamageTaken = player.getLastDamage();
		}
	}
	
	public String replacePlaceholder(String s) {
		String toReplace = s;
		if(targetPlayerUUID != null) {
			toReplace = toReplace.replaceAll("%around_target%", aroundTarget);
			toReplace = toReplace.replaceAll("%around_target_uuid%", targetPlayerUUID.toString());
			toReplace = replaceCalculPlaceholder(toReplace, "%around_target_x%", aroundTargetX+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%around_target_y%", aroundTargetY+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%around_target_z%", aroundTargetZ+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%around_target_x_int%", ((int) aroundTargetX)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%around_target_y_int%", ((int) aroundTargetY)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%around_target_z_int%", ((int) aroundTargetZ)+"", true);
			toReplace = toReplace.replaceAll("%around_target_world%", aroundTargetWorld);
			toReplace = toReplace.replaceAll("%around_target_world_lower%", aroundTargetWorld.toLowerCase());
			toReplace = toReplace.replaceAll("%around_target_slot%", aroundTargetSlot);
			toReplace = replaceCalculPlaceholder(toReplace, "%around_target_last_damage_taken%", aroundTargetLastDamageTaken+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%around_target_last_damage_taken_int%", ((int) aroundTargetLastDamageTaken)+"", true);
		}
		
		return toReplace;
	}

}
