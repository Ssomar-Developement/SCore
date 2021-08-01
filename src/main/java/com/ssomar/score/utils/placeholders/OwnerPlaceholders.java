package com.ssomar.score.utils.placeholders;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class OwnerPlaceholders extends PlaceholdersInterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* placeholders of the player */
	private UUID ownerUUID;
	
	private String owner = "";
	private String ownerX = "";
	private String ownerY = "";
	private String ownerZ = "";
	private String ownerXInt = "";
	private String ownerYInt = "";
	private String ownerZInt = "";
	private String ownerWorld = "";
	private String ownerSlot = "";
	
	public void setOwnerPlcHldr(UUID uuid) {
		this.ownerUUID = uuid;
		this.reloadOwnerPlcHldr();
	}

	public void reloadOwnerPlcHldr() {
		Player player;
		if(this.ownerUUID != null && (player = Bukkit.getPlayer(ownerUUID)) != null) {
			this.owner = player.getName();
			Location pLoc = player.getLocation();
			this.ownerX = pLoc.getX()+"";
			this.ownerY = pLoc.getY()+"";
			this.ownerZ = pLoc.getZ()+"";
			this.ownerXInt = pLoc.getBlockX()+"";
			this.ownerYInt = pLoc.getBlockY()+"";
			this.ownerZInt = pLoc.getBlockZ()+"";
			this.ownerWorld = pLoc.getWorld().getName();
			this.ownerSlot = player.getInventory().getHeldItemSlot()+"";
		}
	}
	
	public String replacePlaceholder(String s) {
		String toReplace = s;
		if(ownerUUID != null) {
			toReplace = toReplace.replaceAll("%owner%", owner);
			toReplace = toReplace.replaceAll("%owner_uuid%", ownerUUID.toString());
			toReplace = replaceCalculPlaceholder(toReplace, "%owner_x%", ownerX, false);
			toReplace = replaceCalculPlaceholder(toReplace, "%owner_y%", ownerY, false);
			toReplace = replaceCalculPlaceholder(toReplace, "%owner_z%", ownerZ, false);
			toReplace = replaceCalculPlaceholder(toReplace, "%owner_x_int%", ownerXInt, true);
			toReplace = replaceCalculPlaceholder(toReplace, "%owner_y_int%", ownerYInt, true);
			toReplace = replaceCalculPlaceholder(toReplace, "%owner_z_int%", ownerZInt, true);
			toReplace = toReplace.replaceAll("%owner_world%", ownerWorld);
			toReplace = toReplace.replaceAll("%owner_toReplacelot%", ownerSlot);
		}
		
		return toReplace;
	}
	
}
