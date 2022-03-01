package com.ssomar.score.utils.placeholders;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.UUID;

public class ProjectilePlaceholders extends PlaceholdersInterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* placeholders of the target projectile */
	private UUID projectileUUID;

	private String projectileType = "";
	private String projectileName = "";
	private double projectileX;
	private double projectileY;
	private double projectileZ;
	@Getter @Setter
	private BlockFace projectileBlockFace = null;
	private String projectileWorld = "";

	public void setProjectilePlcHldr(Projectile projectile, @Nullable BlockFace blockFace) {
		this.projectileUUID = projectile.getUniqueId();
		this.projectileBlockFace = blockFace;
		this.reloadProjectilePlcHldr();
	}

	public void reloadProjectilePlcHldr() {
		Entity entity;
		if(this.projectileUUID != null && (entity = Bukkit.getEntity(projectileUUID)) != null) {
			this.projectileType = entity.getType().toString();
			this.projectileName = entity.getName();
			Location eLoc = entity.getLocation();
			this.projectileX = eLoc.getX();
			this.projectileY = eLoc.getY();
			this.projectileZ = eLoc.getZ();
			this.projectileWorld = eLoc.getWorld().getName();
		}
	}

	public String replacePlaceholder(String s) {
		String toReplace = s;
		if(projectileUUID != null) {
			toReplace = toReplace.replaceAll("%projectile%", projectileType);
			toReplace = toReplace.replaceAll("%projectile_lower_case%", projectileType.toLowerCase());
			toReplace = toReplace.replaceAll("%projectile_name%", projectileName);
			toReplace = toReplace.replaceAll("%projectile_name_lower_case%", projectileName.toLowerCase());
			toReplace = toReplace.replaceAll("%projectile_uuid%", projectileUUID.toString());
			toReplace = replaceCalculPlaceholder(toReplace, "%projectile_x%", projectileX+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%projectile_y%", projectileY+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%projectile_z%", projectileZ+"", false);
			toReplace = replaceCalculPlaceholder(toReplace, "%projectile_x_int%", ((int) projectileX)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%projectile_y_int%", ((int) projectileY)+"", true);
			toReplace = replaceCalculPlaceholder(toReplace, "%projectile_z_int%", ((int) projectileZ)+"", true);
			toReplace = toReplace.replaceAll("%projectileworld%", projectileWorld);
			toReplace = toReplace.replaceAll("%projectile_world%", projectileWorld);
			toReplace = toReplace.replaceAll("%projectile_world_lower%", projectileWorld.toLowerCase());
			if(projectileBlockFace != null){
				toReplace = toReplace.replaceAll("%projectile_blockface%", projectileBlockFace.name().toUpperCase());
				toReplace = toReplace.replaceAll("%projectile_blockface_lower%", projectileBlockFace.name().toLowerCase());
			}
		}

		return toReplace;
	}
}
