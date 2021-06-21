package com.ssomar.score.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;

import me.clip.placeholderapi.PlaceholderAPI;

public class StringPlaceholder {

	/* placeholders of the player */
	private String player= "";
	private String playerUUID="";
	private String x="";
	private String y="";
	private String z="";
	private String world="";
	private String slot="";
	
	/* placeholders of the owner */
	private String owner= "";
	private String ownerUUID="";
	
	/* placeholders of the item */
	private String activator= "";
	private String item= "";
	private String quantity= "";
	private String usage="";
	private String maxUsePerDayItem= "";
	private String maxUsePerDayActivator= "";
	
	/* placeholders of the target entity */
	private String entityUUID="";
	private String entity="";
	private String entityX="";
	private String entityY="";
	private String entityZ="";
	
	/* placeholders of the target player */
	private String targetUUID ="";
	private String target = "";
	private String targetX = "";
	private String targetY = "";
	private String targetZ = "";
	private String targetXInt = "";
	private String targetYInt = "";
	private String targetZInt = "";	
	
	/* placeholders of the block */
	private String block= "";
	private String blockWorld = "";
	private String blockX= "";
	private String blockY= "";
	private String blockZ= "";
	private String blockXInt= "";
	private String blockYInt= "";
	private String blockZInt= "";
	
	/* placeholders of the target block */
	private String targetBlock= "";
	private String targetBlockX= "";
	private String targetBlockY= "";
	private String targetBlockZ= "";
	private String targetBlockXInt= "";
	private String targetBlockYInt= "";
	private String targetBlockZInt= "";
	
	/* placeholders tools */
	private String launcher="";
	private String blockface= "";
	
	/* placeholders of the cooldown */
	private String cooldown= "";
	private String time= "";
	
	/* placeholders of the projectile */
	private String projectileX="";
	private String projectileY="";
	private String projectileZ="";
	
	public void setTargetPlcHldr(Player p) {
		this.setTargetUUID(p.getUniqueId().toString());
		this.setTarget(p.getDisplayName());
		Location pLoc = p.getLocation();
		this.setTargetX(pLoc.getX()+"");
		this.setTargetY(pLoc.getY()+"");
		this.setTargetZ(pLoc.getZ()+"");
		this.setTargetXInt((int) pLoc.getX()+"");
		this.setTargetYInt((int) pLoc.getY()+"");
		this.setTargetZInt((int)pLoc.getZ()+"");
	}

	public String replacePlaceholder(String str) {
		String s = str;
		if(this.hasPlayer()) {
			s=s.replaceAll("%player%", this.getPlayer());
		}
		if(this.hasActivator()) {
			s=s.replaceAll("%activator%", this.getActivator());
		}
		if(this.hasItem()) {
			s=s.replaceAll("%item%", this.getItem());
		}
		if(this.hasQuantity()) {
			s=s.replaceAll("%quantity%", this.getQuantity());
		}
		if(this.hasCoolodwn()) {
			s=s.replaceAll("%cooldown%", this.getCooldown());
		}
		if(this.hasBlock()) {
			s=s.replaceAll("%block%", this.getBlock());
		}
		if(this.hasBlock()) {
			s=s.replaceAll("%block_lower%", this.getBlock().toLowerCase());
		}
		if(this.hasTargetBlock()) {
			s=s.replaceAll("%target_block%", this.getTargetBlock());
		}
		if(this.hasTargetBlock()) {
			s=s.replaceAll("%target_block_lower%", this.getTargetBlock().toLowerCase());
		}
		if(this.hasTarget()) {
			s=s.replaceAll("%target%", this.getTarget());
		}
		if(this.hasTime()) {
			s=s.replaceAll("%time%", this.getTime());
		}
		if(this.hasEntityUUID()) {
			s=s.replaceAll("%entity_uuid%", this.getEntityUUID());
		}
		if(this.hasPlayerUUID()) {
			s=s.replaceAll("%player_uuid%", this.getPlayerUUID());
		}
		if(this.hasTargetUUID()) {
			s=s.replaceAll("%target_uuid%", this.getTarget());
		}
		if(this.hasSlot()) {
			s=s.replaceAll("%slot%", this.getSlot());
		}
		if(this.hasEntity()) {
			s=s.replaceAll("%entity%", this.getEntity().replaceAll("%monster%", this.getEntity()));
		}
		if(this.hasEntityX()) {
			s=this.replaceCalculPlaceholder(s, "%entity_x%", entityX);
		}
		if(this.hasEntityY()) {
			s=this.replaceCalculPlaceholder(s, "%entity_y%", entityY);
		}
		if(this.hasEntityZ()) {
			s=this.replaceCalculPlaceholder(s, "%entity_z%", entityZ);
		}
		if(this.hasX()) {
			s=this.replaceCalculPlaceholder(s, "%x%", x);
		}
		if(this.hasY()) {
			s=this.replaceCalculPlaceholder(s, "%y%", y);
		}
		if(this.hasZ()) {
			s=this.replaceCalculPlaceholder(s, "%z%", z);
		}
		if(this.hasWorld()) {
			s=s.replaceAll("%world%", this.getZ());
		}
		if(this.hasBlockFace()) {
			s=s.replaceAll("%blockface%", this.getBlockface());
		}
		if(this.hasUsage()) {
			s=this.replaceCalculPlaceholder(s, "%usage%", usage);
		}
		if(this.hasProjectileX()) {
			s=this.replaceCalculPlaceholder(s, "%projectile_x%", this.getProjectileX());
		}
		if(this.hasProjectileY()) {
			s=this.replaceCalculPlaceholder(s, "%projectile_y%", this.getProjectileY());
		}
		if(this.hasProjectileZ()) {
			s=this.replaceCalculPlaceholder(s, "%projectile_z%", this.getProjectileZ());
		}
		if(this.hasBlockWorld()) {
			s=this.replaceCalculPlaceholder(s, "%block_world%", this.getBlockWorld());
		}
		if(this.hasBlockX()) {
			s=this.replaceCalculPlaceholder(s, "%block_x%", this.getBlockX());
		}
		if(this.hasBlockY()) {
			s=this.replaceCalculPlaceholder(s, "%block_y%", this.getBlockY());
		}
		if(this.hasBlockZ()) {
			s=this.replaceCalculPlaceholder(s, "%block_z%", this.getBlockZ());
		}
		if(this.hasBlockXInt()) {
			s=this.replaceCalculPlaceholder(s, "%block_x_int%", this.getBlockXInt());
		}
		if(this.hasBlockYInt()) {
			s=this.replaceCalculPlaceholder(s, "%block_y_int%", this.getBlockYInt());
		}
		if(this.hasBlockZInt()) {
			s=this.replaceCalculPlaceholder(s, "%block_z_int%", this.getBlockZInt());
		}
		if(this.hasTargetBlockX()) {
			s=this.replaceCalculPlaceholder(s, "%target_block_x%", this.getTargetBlockX());
		}
		if(this.hasTargetBlockY()) {
			s=this.replaceCalculPlaceholder(s, "%target_block_y%", this.getTargetBlockY());
		}
		if(this.hasTargetBlockZ()) {
			s=this.replaceCalculPlaceholder(s, "%target_block_z%", this.getTargetBlockZ());
		}
		if(this.hasTargetBlockXInt()) {
			s=this.replaceCalculPlaceholder(s, "%target_block_x_int%", this.getTargetBlockXInt());
		}
		if(this.hasTargetBlockYInt()) {
			s=this.replaceCalculPlaceholder(s, "%target_block_y_int%", this.getTargetBlockYInt());
		}
		if(this.hasTargetBlockZInt()) {
			s=this.replaceCalculPlaceholder(s, "%target_block_z_int%", this.getTargetBlockZInt());
		}
		if(this.hasMaxUsePerDayActivator()) {
			s=s.replaceAll("%max_use_per_day_activator%", this.getMaxUsePerDayActivator());
		}
		if(this.hasMaxUsePerDayItem()) {
			s=s.replaceAll("%max_use_per_day_item%", this.getMaxUsePerDayItem());
		}
		if(this.hasOwner()) {
			s = s.replaceAll("%owner%", this.getOwner());
		}
		if(this.hasOwnerUUID()) {
			s = s.replaceAll("%owner_uuid%", this.getOwnerUUID());
		}
		return replacePlaceholderOfPAPI(s);
	}

	public String replacePlaceholderOfPAPI(String s) {
		String replace= s;
		if(this.hasPlayerUUID()) {
			Player p;
			//SsomarDev.testMsg("REPLACE PLACE 2 : "+((p = Bukkit.getPlayer(UUID.fromString(playerUUID)))!=null)+ " &&&&&&& "+ExecutableItems.hasPlaceholderAPI());
			if((p = Bukkit.getPlayer(UUID.fromString(playerUUID)))!=null && SCore.hasPlaceholderAPI) replace = PlaceholderAPI.setPlaceholders(p, replace);
		}
		return replace;
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null)
			return false; 
		try {
			@SuppressWarnings("unused")
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		} 
		return true;
	}
	
	public String replaceCalculPlaceholder(String s, String placeholder, String value) {
		
		String result = s;
		
		while (result.contains(placeholder+"+")) {
			String suit = result.split(placeholder+"\\+")[1];
			StringBuilder sb = new StringBuilder();
			for (char c : suit.toCharArray()) {
				if (c == ' ')
					break; 
				sb.append(c);
			} 
			if (isNumeric(sb.toString())) {
				int d = (int) (Double.parseDouble(sb.toString()) + Double.valueOf(value));
				result = result.replaceAll(placeholder+"\\+" + sb.toString(), "" + d);
			} else {
				result = result.replaceAll(placeholder+"\\+" + sb.toString(), value);
			} 
		}
		
		while (result.contains(placeholder+"-")) {
			String suit = result.split(placeholder+"\\-")[1];
			StringBuilder sb = new StringBuilder();
			for (char c : suit.toCharArray()) {
				if (c == ' ')
					break; 
				sb.append(c);
			} 
			if (isNumeric(sb.toString())) {
				int d = (int) (Double.valueOf(value) - Double.parseDouble(sb.toString()));
				result = result.replaceAll(placeholder+"\\-" + sb.toString(), "" + d);
			} else {
				result = result.replaceAll(placeholder+"\\-" + sb.toString(), value);
			} 
		}
		while (result.contains(placeholder)) {
			result = result.replaceAll(placeholder, value);
		} 
		return result;
	}


	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public boolean hasPlayer() {
		return player.length()!=0;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String player) {
		this.owner = player;
	}
	public boolean hasOwner() {
		return owner.length()!=0;
	}
	public String getLauncher() {
		return launcher;
	}
	public void setLauncher(String launcher) {
		this.launcher = launcher;
	}
	public boolean hasLauncher() {
		return launcher.length()!=0;
	}
	public String getActivator() {
		return activator;
	}
	public void setActivator(String activator) {
		this.activator = activator;
	}
	public boolean hasActivator() {
		return activator.length()!=0;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public boolean hasItem() {
		return item.length()!=0;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public boolean hasQuantity() {
		return quantity.length()!=0;
	}
	public String getCooldown() {
		return cooldown;
	}
	public void setCooldown(String cooldown) {
		this.cooldown = cooldown;
	}
	public boolean hasCoolodwn() {
		return cooldown.length()!=0;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public boolean hasBlock() {
		return block.length()!=0;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public boolean hasTarget() {
		return target.length()!=0;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public boolean hasTime() {
		return time.length()!=0;
	}

	public String getEntityUUID() {
		return entityUUID;
	}
	public void setEntityUUID(String entityUUID) {
		this.entityUUID = entityUUID;
	}
	public boolean hasEntityUUID() {
		return this.entityUUID.length()!=0;
	}
	public String getPlayerUUID() {
		return playerUUID;
	}
	public void setPlayerUUID(String playerUUID) {
		this.playerUUID = playerUUID;
	}
	public boolean hasPlayerUUID() {
		return this.playerUUID.length()!=0;
	}
	public String getOwnerUUID() {
		return ownerUUID;
	}
	public void setOwnerUUID(String playerUUID) {
		this.ownerUUID = playerUUID;
	}
	public boolean hasOwnerUUID() {
		return this.ownerUUID.length()!=0;
	}
	public String getTargetUUID() {
		return targetUUID;
	}
	public void setTargetUUID(String targetUUID) {
		this.targetUUID = targetUUID;
	}
	public boolean hasTargetUUID() {
		return this.targetUUID.length()!=0;
	}
	public String getSlot() {
		return slot;
	}
	public void setSlot(String slot) {
		this.slot = slot;
	}
	public boolean hasSlot() {
		return this.slot.length()!=0;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public boolean hasEntity() {
		return this.entity.length()!=0;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public boolean hasX() {
		return this.x.length()!=0;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public boolean hasY() {
		return this.y.length()!=0;
	}
	public String getZ() {
		return z;
	}
	public void setZ(String z) {
		this.z = z;
	}
	public boolean hasZ() {
		return this.z.length()!=0;
	}
	public String getWorld() {
		return world;
	}
	public void setWorld(String world) {
		this.world = world;
	}
	public boolean hasWorld() {
		return this.world.length()!=0;
	}
	public String getBlockface() {
		return blockface;
	}
	public void setBlockface(String blockface) {
		this.blockface = blockface;
	}
	public boolean hasBlockFace() {
		return this.blockface.length()!=0;
	}

	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public boolean hasUsage() {
		return this.usage.length()!=0;
	}

	public String getProjectileX() {
		return projectileX;
	}

	public void setProjectileX(String projectileX) {
		this.projectileX = projectileX;
	}

	public boolean hasProjectileX() {
		return projectileX.length()!=0;
	}

	public String getProjectileY() {
		return projectileY;
	}

	public void setProjectileY(String projectileY) {
		this.projectileY = projectileY;
	}

	public boolean hasProjectileY() {
		return projectileY.length()!=0;
	}

	public String getProjectileZ() {
		return projectileZ;
	}

	public void setProjectileZ(String projectileZ) {
		this.projectileZ = projectileZ;
	}

	public boolean hasProjectileZ() {
		return projectileZ.length()!=0;
	}
	
	public void setBlockX(String BlockX) {
		this.blockX = BlockX;
	}
	
	public String getBlockX() {
		return blockX;
	}

	public boolean hasBlockX() {
		return blockX.length()!=0;
	}

	public String getBlockY() {
		return blockY;
	}

	public void setBlockY(String BlockY) {
		this.blockY = BlockY;
	}

	public boolean hasBlockY() {
		return blockY.length()!=0;
	}

	public String getBlockZ() {
		return blockZ;
	}

	public void setBlockZ(String BlockZ) {
		this.blockZ = BlockZ;
	}

	public boolean hasBlockZ() {
		return blockZ.length()!=0;
	}

	public String getMaxUsePerDayItem() {
		return maxUsePerDayItem;
	}

	public void setMaxUsePerDayItem(String maxUsePerDayItem) {
		this.maxUsePerDayItem = maxUsePerDayItem;
	}
	
	public boolean hasMaxUsePerDayItem() {
		return maxUsePerDayItem.length()!=0;
	}

	public String getMaxUsePerDayActivator() {
		return maxUsePerDayActivator;
	}

	public void setMaxUsePerDayActivator(String maxUsePerDayActivator) {
		this.maxUsePerDayActivator = maxUsePerDayActivator;
	}
	
	public boolean hasMaxUsePerDayActivator() {
		return maxUsePerDayActivator.length()!=0;
	}

	public boolean hasEntityX() {
		return entityX.length()!=0;
	}
	
	public String getEntityX() {
		return entityX;
	}

	public void setEntityX(String entityX) {
		this.entityX = entityX;
	}
	
	public boolean hasEntityY() {
		return entityY.length()!=0;
	}

	public String getEntityY() {
		return entityY;
	}

	public void setEntityY(String entityY) {
		this.entityY = entityY;
	}

	public boolean hasEntityZ() {
		return entityZ.length()!=0;
	}
	
	public String getEntityZ() {
		return entityZ;
	}

	public void setEntityZ(String entityZ) {
		this.entityZ = entityZ;
	}
	
	public boolean hasBlockXInt() {
		return blockXInt.length()!=0;
	}

	public String getBlockXInt() {
		return blockXInt;
	}

	public void setBlockXInt(String blockXInt) {
		this.blockXInt = blockXInt;
	}
	
	public boolean hasBlockYInt() {
		return blockXInt.length()!=0;
	}

	public String getBlockYInt() {
		return blockYInt;
	}

	public void setBlockYInt(String blockYInt) {
		this.blockYInt = blockYInt;
	}
	
	public boolean hasBlockZInt() {
		return blockXInt.length()!=0;
	}

	public String getBlockZInt() {
		return blockZInt;
	}

	public void setBlockZInt(String blockZInt) {
		this.blockZInt = blockZInt;
	}

	public String getTargetBlock() {
		return targetBlock;
	}

	public void setTargetBlock(String targetBlock) {
		this.targetBlock = targetBlock;
	}
	
	public boolean hasTargetBlock() {
		return targetBlock.length()!=0;
	}

	public String getTargetBlockX() {
		return targetBlockX;
	}

	public void setTargetBlockX(String targetBlockX) {
		this.targetBlockX = targetBlockX;
	}
	
	public boolean hasTargetBlockX() {
		return this.targetBlockX.length()!=0;
	}

	public String getTargetBlockY() {
		return targetBlockY;
	}

	public void setTargetBlockY(String targetBlockY) {
		this.targetBlockY = targetBlockY;
	}
	
	public boolean hasTargetBlockY() {
		return this.targetBlockY.length()!=0;
	}

	public String getTargetBlockZ() {
		return targetBlockZ;
	}

	public void setTargetBlockZ(String targetBlockZ) {
		this.targetBlockZ = targetBlockZ;
	}
	
	public boolean hasTargetBlockZ() {
		return this.targetBlockZ.length()!=0;
	}

	public String getTargetBlockXInt() {
		return targetBlockXInt;
	}

	public void setTargetBlockXInt(String targetBlockXInt) {
		this.targetBlockXInt = targetBlockXInt;
	}
	
	public boolean hasTargetBlockXInt() {
		return this.targetBlockXInt.length()!=0;
	}

	public String getTargetBlockYInt() {
		return targetBlockYInt;
	}

	public void setTargetBlockYInt(String targetBlockYInt) {
		this.targetBlockYInt = targetBlockYInt;
	}
	
	public boolean hasTargetBlockYInt() {
		return this.targetBlockYInt.length()!=0;
	}

	public String getTargetBlockZInt() {
		return targetBlockZInt;
	}

	public void setTargetBlockZInt(String targetBlockZInt) {
		this.targetBlockZInt = targetBlockZInt;
	}
	
	public boolean hasTargetBlockZInt() {
		return this.targetBlockZInt.length()!=0;
	}
	
	public boolean hasBlockWorld() {
		return blockWorld.length()!=0;
	}

	public String getBlockWorld() {
		return blockWorld;
	}

	public void setBlockWorld(String blockWorld) {
		this.blockWorld = blockWorld;
	}
	
	public boolean hasTargetX() {
		return targetX.length()!=0;
	}

	public String getTargetX() {
		return targetX;
	}

	public void setTargetX(String targetX) {
		this.targetX = targetX;
	}
	
	public boolean hasTargetY() {
		return targetX.length()!=0;
	}

	public String getTargetY() {
		return targetY;
	}

	public void setTargetY(String targetY) {
		this.targetY = targetY;
	}
	
	public boolean hasTargetZ() {
		return targetX.length()!=0;
	}

	public String getTargetZ() {
		return targetZ;
	}

	public void setTargetZ(String targetZ) {
		this.targetZ = targetZ;
	}
	
	public boolean hasTargetXInt() {
		return targetXInt.length()!=0;
	}

	public String getTargetXInt() {
		return targetXInt;
	}

	public void setTargetXInt(String targetXInt) {
		this.targetXInt = targetXInt;
	}
	
	public boolean hasTargetYInt() {
		return targetXInt.length()!=0;
	}

	public String getTargetYInt() {
		return targetYInt;
	}

	public void setTargetYInt(String targetYInt) {
		this.targetYInt = targetYInt;
	}
	
	public boolean hasTargetZInt() {
		return targetXInt.length()!=0;
	}

	public String getTargetZInt() {
		return targetZInt;
	}

	public void setTargetZInt(String targetZInt) {
		this.targetZInt = targetZInt;
	}
		
	
}
