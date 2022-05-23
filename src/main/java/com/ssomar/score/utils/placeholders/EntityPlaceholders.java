package com.ssomar.score.utils.placeholders;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class EntityPlaceholders extends PlaceholdersInterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* placeholders of the target entity */
	private UUID entityUUID;

	private String entityType = "";
	private String entityName = "";
	private double entityX;
	private double entityY ;
	private double entityZ ;
	private String entityWorld = "";
	private float pitch;
	private float pitchPositive;
	private float yaw;
	private float yawPositive;
	private String entityDirection;
	private double entityHealth;
	private double entityMaxHealth;

	public void setEntityPlcHldr(UUID uuid) {
		this.entityUUID = uuid;
		this.reloadEntityPlcHldr();
	}

	public void reloadEntityPlcHldr() {
		Entity entity;
		if(this.entityUUID != null && (entity = Bukkit.getEntity(entityUUID)) != null) {
			this.entityType = entity.getType().toString();
			this.entityName = entity.getName();
			Location eLoc = entity.getLocation();
			this.entityX = eLoc.getX();
			this.entityY = eLoc.getY();
			this.entityZ = eLoc.getZ();
			this.entityWorld = eLoc.getWorld().getName();
			this.pitch = eLoc.getPitch();
			if(pitch < 0) pitchPositive = pitch * -1;
			else pitchPositive = pitch;
			this.yaw = eLoc.getYaw();
			if(yaw < 0) yawPositive = yaw * -1;
			else yawPositive = yaw;
			float yaw = eLoc.getYaw();
			if(yaw >= -30 && yaw <= 30){
				entityDirection = "S";
			}
			else if(yaw > 30 && yaw < 60){
				entityDirection = "SW";
			}
			else if(yaw >= 60 && yaw <= 120){
				entityDirection = "W";
			}
			else if(yaw > 120 && yaw < 150){
				entityDirection = "NW";
			}
			else if(yaw >= 150 || yaw <= -150){
				entityDirection = "N";
			}
			else if(yaw > -150 && yaw <-120 ){
				entityDirection = "NE";
			}
			else if(yaw >= -120 && yaw <= -60){
				entityDirection = "E";
			}
			else if(yaw > -60 && yaw <-30 ){
				entityDirection = "SE";
			}
			if(entity instanceof LivingEntity){
				LivingEntity lE = (LivingEntity) entity;
				this.entityMaxHealth = lE.getMaxHealth();
				this.entityHealth = lE.getHealth();
			}
			else{
				this.entityMaxHealth = -1;
				this.entityHealth = -1;
			}
		}
	}

	public String replacePlaceholder(String s) {
		String toReplace = s;
		if(entityUUID != null) {
			toReplace = toReplace.replaceAll("%entity%", entityType);
			toReplace = toReplace.replaceAll("%entity_lower_case%", entityType.toLowerCase());
			toReplace = toReplace.replaceAll("%entity_name%", entityName);
			toReplace = toReplace.replaceAll("%entity_name_lower_case%", entityName.toLowerCase());
			toReplace = toReplace.replaceAll("%entity_uuid%", entityUUID.toString());
			toReplace = replaceCalculPlaceholder(toReplace, "%entity_x%", entityX+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%entity_y%", entityY+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%entity_z%", entityZ+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%entity_x_int%", ((int) entityX)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%entity_y_int%", ((int) entityY)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%entity_z_int%", ((int) entityZ)+"", true);
			toReplace = toReplace.replaceAll("%entityworld%", entityWorld);
			toReplace = toReplace.replaceAll("%entity_world%", entityWorld);
			toReplace = toReplace.replaceAll("%entity_world_lower%", entityWorld.toLowerCase());

			toReplace = replaceCalculPlaceholder(toReplace, "%entity_pitch%", pitch+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%entity_pitch_int%", ((int) pitch)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%entity_pitch_positive%", pitchPositive+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%entity_pitch_positive_int%", ((int) pitchPositive)+"", false);

			toReplace = toReplace.replaceAll("%entity_direction%", entityDirection);
			if(entityMaxHealth != -1) toReplace = replaceCalculPlaceholder(toReplace, "%entity_max_health%", entityMaxHealth+"", false);
			if(entityHealth != -1) toReplace = replaceCalculPlaceholder(toReplace, "%entity_health%", entityHealth+"", false);
		}

		return toReplace;
	}
}
