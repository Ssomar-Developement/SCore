package com.ssomar.score.utils.placeholders;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class PlayerPlaceholders extends PlaceholdersInterface implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* placeholders of the player */
	private UUID playerUUID;
	private int fixSlot;
	
	private String player = "";
	private double x;
	private double y;
	private double z;
	private String world = "";
	private String slot = "";
	private String slotLive = "";
	private double lastDamageTaken;
	private float pitch;
	private float pitchPositive;
	private String direction;
	
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
			this.x = pLoc.getX();
			this.y = pLoc.getY();
			this.z = pLoc.getZ();
			this.world = pLoc.getWorld().getName();
			if(fixSlot != -1) this.slot = fixSlot+"";
			else this.slot = player.getInventory().getHeldItemSlot()+"";
			this.slotLive = player.getInventory().getHeldItemSlot()+"";
			this.lastDamageTaken = player.getLastDamage();
			this.pitch = pLoc.getPitch();
			if(pitch < 0) pitchPositive = pitch * -1;
			else pitchPositive = pitch;
		 	float yaw = pLoc.getYaw();
		 	if(yaw >= -30 && yaw <= 30){
		 		direction = "S";
			}
		 	else if(yaw > 30 && yaw < 60){
				direction = "SW";
			}
			else if(yaw >= 60 && yaw <= 120){
				direction = "W";
			}
			else if(yaw > 120 && yaw < 150){
				direction = "NW";
			}
			else if(yaw >= 150 || yaw <= -150){
				direction = "N";
			}
			else if(yaw > -150 && yaw <-120 ){
				direction = "NE";
			}
			else if(yaw >= -120 && yaw <= -60){
				direction = "E";
			}
			else if(yaw > -60 && yaw <-30 ){
				direction = "SE";
			}
		}
	}
	
	public String replacePlaceholder(String s) {
		String toReplace = s;
		if(playerUUID != null) {
			toReplace = toReplace.replaceAll("%player%", player);
			toReplace = toReplace.replaceAll("%player_name%", player);
			toReplace = toReplace.replaceAll("%player_uuid%", playerUUID.toString());
			toReplace = replaceCalculPlaceholder(toReplace, "%x%", x+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%y%", y+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%z%", z+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%x_int%", ((int) x)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%y_int%", ((int) y)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%z_int%", ((int) z)+"", true);
			toReplace = toReplace.replaceAll("%world%", world);
			toReplace = toReplace.replaceAll("%slot%", slot);
			toReplace = toReplace.replaceAll("%slot_live%", slotLive);
			toReplace = replaceCalculPlaceholder(toReplace, "%last_damage_taken%", lastDamageTaken+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%last_damage_taken_int%", ((int) lastDamageTaken)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%pitch%", pitch+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%pitch_int%", ((int) pitch)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%pitch_positive%", pitchPositive+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%pitch_positive_int%", ((int) pitchPositive)+"", false);
			toReplace = toReplace.replaceAll("%direction%", direction);


			toReplace = replaceCalculPlaceholder(toReplace, "%player_x%", x+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%player_y%", y+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%player_z%", z+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%player_x_int%", ((int) x)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%player_y_int%", ((int) y)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%player_z_int%", ((int) z)+"", true);
			toReplace = toReplace.replaceAll("%player_world%", world);
			toReplace = toReplace.replaceAll("%player_slot%", slot);
			toReplace = toReplace.replaceAll("%player_slot_live%", slotLive);
			toReplace = replaceCalculPlaceholder(toReplace, "%player_last_damage_taken%", lastDamageTaken+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%player_last_damage_taken_int%", ((int) lastDamageTaken)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%player_pitch%", pitch+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%player_pitch_int%", ((int) pitch)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%player_pitch_positive%", pitchPositive+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%player_pitch_positive_int%", ((int) pitchPositive)+"", false);
			toReplace = toReplace.replaceAll("%player_direction%", direction);
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
