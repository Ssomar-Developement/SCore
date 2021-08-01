package com.ssomar.score.utils.placeholders;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class OwnerPlaceholders extends PlaceholdersInterface {

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
		if(ownerUUID != null) {
			s = s.replaceAll("%owner%", owner);
			s = s.replaceAll("%owner_uuid%", ownerUUID.toString());
			s = replaceCalculPlaceholder(s, "%owner_x%", ownerX, false);
			s = replaceCalculPlaceholder(s, "%owner_y%", ownerY, false);
			s = replaceCalculPlaceholder(s, "%owner_z%", ownerZ, false);
			s = replaceCalculPlaceholder(s, "%owner_x_int%", ownerXInt, true);
			s = replaceCalculPlaceholder(s, "%owner_y_int%", ownerYInt, true);
			s = replaceCalculPlaceholder(s, "%owner_z_int%", ownerZInt, true);
			s = s.replaceAll("%owner_world%", ownerWorld);
			s = s.replaceAll("%owner_slot%", ownerSlot);
		}
		
		return s;
	}
	
}
