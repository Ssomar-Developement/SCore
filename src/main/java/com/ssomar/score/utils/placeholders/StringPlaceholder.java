package com.ssomar.score.utils.placeholders;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;

import me.clip.placeholderapi.PlaceholderAPI;

public class StringPlaceholder extends PlaceholdersInterface{
	
	/* placeholders of the player */
	private PlayerPlaceholders playerPlch = new PlayerPlaceholders();
	
	/* placeholders of the target player */
	private TargetPlaceholders targetPlch = new TargetPlaceholders();

	/* placeholders of the owner */
	private OwnerPlaceholders ownerPlch = new OwnerPlaceholders();

	/* placeholders of the item */
	private String activator= "";
	private String item= "";
	private String quantity= "";
	private String usage="";
	private String maxUsePerDayItem= "";
	private String maxUsePerDayActivator= "";

	/* placeholders of the target entity */
	private EntityPlaceholders entityPlch = new EntityPlaceholders();

	/* placeholders of the block */
	private BlockPlaceholders blockPlch = new BlockPlaceholders();

	/* placeholders of the target block */
	private TargetBlockPlaceholders targetBlockPlch = new TargetBlockPlaceholders();

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
	
	public void setPlayerPlcHldr(UUID uuid) {
		 playerPlch.setPlayerPlcHldr(uuid);
	}
	
	public void setTargetPlcHldr(UUID uuid) {
		 targetPlch.setTargetPlcHldr(uuid);
	}
	
	public void setOwnerPlcHldr(UUID uuid) {
		 ownerPlch.setOwnerPlcHldr(uuid);
	}
	
	public void setEntityPlcHldr(UUID uuid) {
		 entityPlch.setEntityPlcHldr(uuid);
	}
	
	public void setBlockPlcHldr(Block block) {
		 blockPlch.setBlockPlcHldr(block);
	}
	
	public void setTargetBlockPlcHldr(Block block) {
		 targetBlockPlch.setTargetBlockPlcHldr(block);
	}
	
	public void reloadAllPlaceholders() {
		playerPlch.reloadPlayerPlcHldr();
		targetPlch.reloadTargetPlcHldr();
		ownerPlch.reloadOwnerPlcHldr();
		entityPlch.reloadEntityPlcHldr();
		blockPlch.reloadBlockPlcHldr();
		targetBlockPlch.reloadTargetBlockPlcHldr();
	}

	public String replacePlaceholder(String str) {
		this.reloadAllPlaceholders();
		String s = str;
		if(this.hasActivator()) {
			s = s.replaceAll("%activator%", this.getActivator());
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
		if(this.hasTime()) {
			s=s.replaceAll("%time%", this.getTime());
		}
		if(this.hasBlockFace()) {
			s=s.replaceAll("%blockface%", this.getBlockface());
		}
		if(this.hasUsage()) {
			s = replaceCalculPlaceholder(s, "%usage%", usage, true);
		}
		if(this.hasProjectileX()) {
			s = replaceCalculPlaceholder(s, "%projectile_x%", this.getProjectileX(), false);
		}
		if(this.hasProjectileY()) {
			s = replaceCalculPlaceholder(s, "%projectile_y%", this.getProjectileY(), false);
		}
		if(this.hasProjectileZ()) {
			s = replaceCalculPlaceholder(s, "%projectile_z%", this.getProjectileZ(), false);
		}
		if(this.hasMaxUsePerDayActivator()) {
			s=s.replaceAll("%max_use_per_day_activator%", this.getMaxUsePerDayActivator());
		}
		if(this.hasMaxUsePerDayItem()) {
			s=s.replaceAll("%max_use_per_day_item%", this.getMaxUsePerDayItem());
		}
		
		s = playerPlch.replacePlaceholder(s);
		
		s = targetPlch.replacePlaceholder(s);
		
		s = ownerPlch.replacePlaceholder(s);
		
		s = entityPlch.replacePlaceholder(s);
		
		s = blockPlch.replacePlaceholder(s);
		
		s = targetBlockPlch.replacePlaceholder(s);
		
		return replacePlaceholderOfPAPI(s);
	}

	public String replacePlaceholderOfPAPI(String s) {
		String replace = s;
		UUID uuid;
		if((uuid = playerPlch.getPlayerUUID()) != null) {
			Player p;
			//SsomarDev.testMsg("REPLACE PLACE 2 : "+((p = Bukkit.getPlayer(UUID.fromString(playerUUID)))!=null)+ " &&&&&&& "+ExecutableItems.hasPlaceholderAPI());
			if((p = Bukkit.getPlayer(uuid)) != null && SCore.hasPlaceholderAPI) replace = PlaceholderAPI.setPlaceholders(p, replace);
		}
		return replace;
	}

	//	public static void main(String[] args) {
	//		 StringPlaceholder sp = new StringPlaceholder();
	//		 sp.blockXInt = "10";
	//		 sp.blockYInt = "11";
	//		 sp.blockZInt = "12";
	//		 
	//		 String base = "SENDMESSAGE oops %block_x_int%,%block_y_int%+1,%block_z_int% youhouu";
	//		 
	//		 base = sp.replacePlaceholder(base);
	//		 
	//		 System.out.println(base);
	//	}


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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public boolean hasTime() {
		return time.length()!=0;
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

}
