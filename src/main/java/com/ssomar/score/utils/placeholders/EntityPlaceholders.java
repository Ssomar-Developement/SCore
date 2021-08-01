package com.ssomar.score.utils.placeholders;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class EntityPlaceholders extends PlaceholdersInterface{

	/* placeholders of the target entity */
	private UUID entityUUID;

	private String entity = "";
	private String entityX = "";
	private String entityY = "";
	private String entityZ = "";
	private String entityXInt = "";
	private String entityYInt = "";
	private String entityZInt = "";
	private String entityWorld = "";

	public void setEntityPlcHldr(UUID uuid) {
		this.entityUUID = uuid;
		this.reloadEntityPlcHldr();
	}

	public void reloadEntityPlcHldr() {
		Entity entity;
		if(this.entityUUID != null && (entity = Bukkit.getEntity(entityUUID)) != null) {
			this.entity = entity.getName();
			Location eLoc = entity.getLocation();
			this.entityX = eLoc.getX()+"";
			this.entityY = eLoc.getY()+"";
			this.entityZ = eLoc.getZ()+"";
			this.entityXInt = eLoc.getBlockX()+"";
			this.entityYInt = eLoc.getBlockY()+"";
			this.entityZInt = eLoc.getBlockZ()+"";
			this.entityWorld = eLoc.getWorld().getName();
		}
	}

	public String replacePlaceholder(String s) {
		if(entityUUID != null) {
			s = s.replaceAll("%entity%", entity);
			s = s.replaceAll("%entity_uuid%", entityUUID.toString());
			s = replaceCalculPlaceholder(s, "%entity_x%", entityX, false);
			s = replaceCalculPlaceholder(s, "%entity_y%", entityY, false);
			s = replaceCalculPlaceholder(s, "%entity_z%", entityZ, false);
			s = replaceCalculPlaceholder(s, "%entity_x_int%", entityXInt, true);
			s = replaceCalculPlaceholder(s, "%entity_y_int%", entityYInt, true);
			s = replaceCalculPlaceholder(s, "%entity_z_int%", entityZInt, true);
			s = s.replaceAll("%entityworld%", entityWorld);
		}

		return s;
	}
}
