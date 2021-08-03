package com.ssomar.score.utils.placeholders;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class AroundEntityTargetPlaceholders extends PlaceholdersInterface implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* placeholders of the player */
	private UUID targetEntityUUID;
	
	private String aroundTargetType = "";
	private String aroundTargetName = "";
	private String aroundTargetX = "";
	private String aroundTargetY = "";
	private String aroundTargetZ = "";
	private String aroundTargetXInt = "";
	private String aroundTargetYInt = "";
	private String aroundTargetZInt = "";
	private String aroundTargetWorld = "";

	
	public void setAroundEntityTargetPlcHldr(UUID uuid) {
		this.targetEntityUUID = uuid;
		this.reloadAroundEntityTargetPlcHldr();
	}

	public void reloadAroundEntityTargetPlcHldr() {
		Entity entity;
		if(this.targetEntityUUID != null && (entity = Bukkit.getPlayer(targetEntityUUID)) != null) {
			this.aroundTargetName = entity.getName();
			this.aroundTargetType = entity.getType().toString();
			Location pLoc = entity.getLocation();
			this.aroundTargetX = pLoc.getX()+"";
			this.aroundTargetY = pLoc.getY()+"";
			this.aroundTargetZ = pLoc.getZ()+"";
			this.aroundTargetXInt = pLoc.getBlockX()+"";
			this.aroundTargetYInt = pLoc.getBlockY()+"";
			this.aroundTargetZInt = pLoc.getBlockZ()+"";
			this.aroundTargetWorld = pLoc.getWorld().getName();
		}
	}
	
	public String replacePlaceholder(String s) {
		String toReplace = s;
		if(targetEntityUUID != null) {
			toReplace = toReplace.replaceAll("%around_target_name%", aroundTargetName);
			toReplace = toReplace.replaceAll("%around_target%", aroundTargetType);
			toReplace = toReplace.replaceAll("%around_target_uuid%", targetEntityUUID.toString());
			toReplace = replaceCalculPlaceholder(toReplace, "%around_target_x%", aroundTargetX, false);
			toReplace = replaceCalculPlaceholder(toReplace, "%around_target_y%", aroundTargetY, false);
			toReplace = replaceCalculPlaceholder(toReplace, "%around_target_z%", aroundTargetZ, false);
			toReplace = replaceCalculPlaceholder(toReplace, "%around_target_x_int%", aroundTargetXInt, true);
			toReplace = replaceCalculPlaceholder(toReplace, "%around_target_y_int%", aroundTargetYInt, true);
			toReplace = replaceCalculPlaceholder(toReplace, "%around_target_z_int%", aroundTargetZInt, true);
			toReplace = toReplace.replaceAll("%around_target_world%", aroundTargetWorld);
		}
		
		return toReplace;
	}

}
